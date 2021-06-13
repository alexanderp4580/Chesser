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
    // AndroidX
    implementation(AndroidX.lifecycle.viewModelKtx)
    implementation(AndroidX.fragmentKtx)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.core.ktx)

    implementation(AndroidX.navigation.fragmentKtx)
    implementation(AndroidX.navigation.uiKtx)
}