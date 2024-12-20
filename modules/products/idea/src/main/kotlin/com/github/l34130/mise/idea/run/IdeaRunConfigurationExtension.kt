package com.github.l34130.mise.idea.run

import com.github.l34130.mise.core.command.MiseCommandLine
import com.github.l34130.mise.core.run.MiseRunConfigurationSettingsEditor
import com.github.l34130.mise.core.setting.MiseSettings
import com.intellij.execution.RunConfigurationExtension
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.configurations.JavaParameters
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunnerSettings
import com.intellij.openapi.components.service
import com.intellij.openapi.options.SettingsEditor
import org.jdom.Element

class IdeaRunConfigurationExtension : RunConfigurationExtension() {
    override fun getEditorTitle(): String = MiseRunConfigurationSettingsEditor.EDITOR_TITLE

    override fun <P : RunConfigurationBase<*>> createEditor(configuration: P): SettingsEditor<P> =
        MiseRunConfigurationSettingsEditor(configuration.project)

    override fun getSerializationId(): String = MiseRunConfigurationSettingsEditor.SERIALIZATION_ID

    override fun readExternal(
        runConfiguration: RunConfigurationBase<*>,
        element: Element,
    ) {
        MiseRunConfigurationSettingsEditor.readExternal(runConfiguration, element)
    }

    override fun writeExternal(
        runConfiguration: RunConfigurationBase<*>,
        element: Element,
    ) {
        MiseRunConfigurationSettingsEditor.writeExternal(runConfiguration, element)
    }

    override fun <T : RunConfigurationBase<*>> updateJavaParameters(
        configuration: T,
        params: JavaParameters,
        runnerSettings: RunnerSettings?,
    ) {
        val sourceEnv =
            GeneralCommandLine()
                .withEnvironment(params.env)
                .withParentEnvironmentType(
                    if (params.isPassParentEnvs) {
                        GeneralCommandLine.ParentEnvironmentType.CONSOLE
                    } else {
                        GeneralCommandLine.ParentEnvironmentType.NONE
                    },
                ).effectiveEnvironment
        params.env.putAll(sourceEnv)

        val projectState = configuration.project.service<MiseSettings>().state
        val runConfigState = MiseRunConfigurationSettingsEditor.getMiseRunConfigurationState(configuration)

        when {
            projectState.useMiseDirEnv -> {
                params.env.putAll(
                    MiseCommandLine(configuration.project).loadEnvironmentVariables(profile = projectState.miseProfile),
                )
            }

            runConfigState?.useMiseDirEnv == true -> {
                params.env.putAll(
                    MiseCommandLine(
                        configuration.project,
                        params.workingDirectory,
                    ).loadEnvironmentVariables(profile = runConfigState.miseProfile),
                )
            }
        }

        val miseState = MiseRunConfigurationSettingsEditor.getMiseRunConfigurationState(configuration)
        if (miseState?.useMiseDirEnv == true) {
            params.env.putAll(
                MiseCommandLine(project = configuration.project).loadEnvironmentVariables(profile = miseState.miseProfile),
            )
        }
    }

    override fun isApplicableFor(configuration: RunConfigurationBase<*>): Boolean = true

    override fun isEnabledFor(
        applicableConfiguration: RunConfigurationBase<*>,
        runnerSettings: RunnerSettings?,
    ): Boolean = true
}
