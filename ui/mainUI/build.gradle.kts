plugins {
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(projects.commonUI)
    implementation(projects.homeUI)
    implementation(projects.timerUI)

    // AndroidX
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.navigation.fragmentKtx)
    implementation(AndroidX.navigation.uiKtx)
}