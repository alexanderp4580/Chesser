plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = AppSettings.APP_ID
    }
}

dependencies {
    api(projects.common)

    implementation(AndroidX.core)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.constraintLayout)
}
