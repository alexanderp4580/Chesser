package com.alexanderp.chesser.timerUI.ktx

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController

@Suppress("DEPRECATION")
fun Window.enableFullScreen() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        insetsController?.apply {
            hide(WindowInsets.Type.statusBars())
            systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else {
        decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            )
    }
}

@Suppress("DEPRECATION")
fun Window.disableFullScreen() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        insetsController?.apply {
            show(WindowInsets.Type.statusBars())
            systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_TOUCH
        }
    } else {
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}
