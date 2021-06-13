pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }

    plugins {
        id("de.fayard.refreshVersions").version("10.0.1")
    }
}

plugins {
    id("de.fayard.refreshVersions")
}