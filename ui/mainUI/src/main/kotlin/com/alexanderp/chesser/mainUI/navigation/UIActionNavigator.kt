package com.alexanderp.chesser.mainUI.navigation

import androidx.navigation.NavController
import com.alexanderp.chesser.homeUI.navigation.HomeNavigationActions
import com.alexanderp.chesser.mainUI.NavigationRootDirections
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

/**
 * Navigator for traveling between modules.
 *
 * Based on https://proandroiddev.com/structural-and-navigation-anti-patterns-in-modularized-android-applications-a7d667e35cd6
 */
@ActivityScoped
class UIActionNavigator @Inject constructor(
    private val navController: NavController
) : HomeNavigationActions {
    override fun navigateToTimerSettings() {
        navController.navigate(NavigationRootDirections.actionHomeFragmentToTimerSettingsFragment())
    }
}
