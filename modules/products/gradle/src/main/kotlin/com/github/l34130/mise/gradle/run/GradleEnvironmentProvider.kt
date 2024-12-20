package com.github.l34130.mise.gradle.run

import com.github.l34130.mise.core.command.MiseCommandLine
import com.github.l34130.mise.core.setting.MiseSettings
import com.intellij.execution.Executor
import com.intellij.execution.application.ApplicationConfiguration
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.project.Project
import com.intellij.task.ExecuteRunConfigurationTask
import org.jetbrains.plugins.gradle.execution.build.GradleExecutionEnvironmentProvider
import org.jetbrains.plugins.gradle.service.execution.GradleRunConfiguration

class GradleEnvironmentProvider : GradleExecutionEnvironmentProvider {
    override fun isApplicable(task: ExecuteRunConfigurationTask?): Boolean = task?.runProfile is ApplicationConfiguration

    override fun createExecutionEnvironment(
        project: Project,
        task: ExecuteRunConfigurationTask,
        executor: Executor,
    ): ExecutionEnvironment? {
        val environment =
            GradleExecutionEnvironmentProvider.EP_NAME.extensions
                .firstOrNull { provider ->
                    provider != this && provider.isApplicable(task)
                }?.createExecutionEnvironment(project, task, executor)

        if (MiseSettings
                .getService(project)
                .state.useMiseDirEnv
                .not()
        ) {
            return environment
        }

        if (environment?.runProfile is GradleRunConfiguration) {
            val sourceConfig = task.runProfile as ApplicationConfiguration
            val gradleConfig = environment.runProfile as GradleRunConfiguration

            gradleConfig.settings.env = MiseCommandLine(
                project = project,
                workDir = sourceConfig.project.basePath,
            ).loadEnvironmentVariables(profile = MiseSettings.getService(project).state.miseProfile) + sourceConfig.envs
        }

        return environment
    }
}
