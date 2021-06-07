import com.android.build.gradle.TestedExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    kotlin("android") apply false
    id("io.gitlab.arturbosch.detekt")
    id("org.jlleitschuh.gradle.ktlint")
    id("org.jetbrains.dokka") version "1.4.32"
}

allprojects {
    group = PUBLISHING_GROUP
    repositories {
        google()
        mavenCentral()
    }
}

val jvmTargetVersion = JavaVersion.VERSION_1_8.toString()

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("org.jetbrains.dokka")
    }

    ktlint {
        debug.set(false)
        verbose.set(true)
        android.set(true)
        outputToConsole.set(true)
        ignoreFailures.set(false)
        enableExperimentalRules.set(true)
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }

    if (name == "app") {
        plugins.apply("com.android.application")
    } else {
        plugins.apply("com.android.library")
    }

    plugins.apply("kotlin-android")
    plugins.apply("kotlin-kapt")

    android {
        compileSdkVersion(Sdk.COMPILE_SDK_VERSION)

        defaultConfig {
            minSdkVersion(Sdk.MIN_SDK_VERSION)
            targetSdkVersion(Sdk.TARGET_SDK_VERSION)

            versionCode = AppSettings.APP_VERSION_CODE
            versionName = AppSettings.APP_VERSION_NAME
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        sourceSets {
            named("main") {
                java.srcDirs("src/main/kotlin")
            }

            named("test") {
                java.srcDir("src/test/kotlin")
            }

            named("androidTest") {
                java.srcDir("src/androidTest/kotlin")
            }
        }

        lintOptions {
            isWarningsAsErrors = true
            isAbortOnError = true
            disable("VectorPath")
        }

        compileOptions {
            sourceCompatibility(jvmTargetVersion)
            targetCompatibility(jvmTargetVersion)
        }

        dependencies {
            // Bringing configurations to have them available at script compile time.
            val implementation by configurations
            val testImplementation by configurations
            val androidTestImplementation by configurations

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")
            implementation("io.github.microutils:kotlin-logging:_")

            testImplementation(Kotlin.Test.junit)

            androidTestImplementation(AndroidX.test.ext.junit)
            androidTestImplementation(AndroidX.test.ext.junitKtx)
            androidTestImplementation(AndroidX.test.rules)
            androidTestImplementation(AndroidX.test.espresso.core)
        }
    }

    detekt {
        config = rootProject.files("config/detekt/detekt.yml")
        reports {
            html {
                enabled = true
                destination = file("build/reports/detekt.html")
            }
        }
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = jvmTargetVersion
        targetCompatibility = jvmTargetVersion
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = jvmTargetVersion
            allWarningsAsErrors = true
            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
        }
    }
}

val Project.android: TestedExtension
    get() = extensions.getByType(TestedExtension::class.java)

fun Project.android(action: Action<TestedExtension>) {
    action.execute(android)
}

tasks {
    register("clean", Delete::class.java) {
        delete(rootProject.buildDir)
    }
}
