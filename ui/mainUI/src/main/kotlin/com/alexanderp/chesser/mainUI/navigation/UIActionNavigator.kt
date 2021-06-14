package com.alexanderp.chesser.mainUI.navigation

import androidx.navigation.NavController
import com.alexanderp.chesser.homeUI.navigation.HomeNavigationActions
import com.alexanderp.chesser.mainUI.NavigationRootDirections
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class UIActionNavigator @Inject constructor(
    private val navController: NavController
) : HomeNavigationActions {
    override fun navigateToTimerSettings() {
        navController.navigate(NavigationRootDirections.actionHomeFragmentToTimerSettingsFragment())
    }
}