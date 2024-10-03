import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType

fun properties(key: String) = project.findProperty(key).toString()

plugins {
    id("org.jetbrains.gradle.plugin.idea-ext")
    id("org.jetbrains.intellij.platform.module")
    alias(libs.plugins.kotlin) // Kotlin support
}

dependencies {
    implementation(project(":mise-core"))

    // Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
    intellijPlatform {
        create(IntelliJPlatformType.IntellijIdeaCommunity, properties("platformVersion"))

        bundledPlugins("com.intellij.java", "com.intellij.gradle")

        instrumentationTools()
    }
}
