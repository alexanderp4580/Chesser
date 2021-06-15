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
    implementation(projects.timerUI)

    // AndroidX
    implementation(AndroidX.fragmentKtx)
    implementation(AndroidX.lifecycle.viewModelKtx)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.navigation.fragmentKtx)
    implementation(AndroidX.navigation.uiKtx)
}
