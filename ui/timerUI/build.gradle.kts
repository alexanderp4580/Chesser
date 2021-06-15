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
    implementation(projects.timer)
    implementation(projects.commonUI)
    implementation(projects.common)

    // AndroidX
    implementation(AndroidX.lifecycle.viewModelKtx)
    implementation(AndroidX.fragmentKtx)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.core.ktx)
    implementation(Google.Android.Material)
    implementation(AndroidX.cardView)
    implementation(AndroidX.lifecycle.liveDataKtx)

    implementation(AndroidX.navigation.fragmentKtx)
    implementation(AndroidX.navigation.uiKtx)
}