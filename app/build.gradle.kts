plugins {
    id("com.android.application")
    kotlin("android")
    id("dagger.hilt.android.plugin")
}

android {
    buildFeatures {
        viewBinding = true
    }

    hilt {
        enableAggregatingTask = true
    }

    defaultConfig {
        applicationId = AppSettings.APP_ID
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.mainUI)

    // AndroidX
    implementation(AndroidX.appCompat)
}
