pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.android.library") {
                useModule("com.android.tools.build:gradle:${requested.version}")
            }
            if (requested.id.id == "com.android.application") {
                useModule("com.android.tools.build:gradle:${requested.version}")
            }
        }
    }
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.10.1"
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = ("chesser")
val repoRootDir: File = rootProject.projectDir

val projects = listOf(
    "app",
    "common",
    "ui/commonUI",
    "ui/mainUI",
    "ui/homeUI",
    "ui/timerUI",
    "domain/timer",
).map {
    "$it/build.gradle.kts"
}

val gradleBuildTree = fileTree(repoRootDir) {
    include(projects)
}

gradleBuildTree.forEach { file ->
    val parentDir = file.parentFile
    val projectName = ":${parentDir.name}"
    include(projectName)
    project(projectName).projectDir = parentDir
}