package com.alexanderp.chesser.mainUI.di

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.alexanderp.chesser.homeUI.navigation.HomeNavigationActions
import com.alexanderp.chesser.mainUI.R
import com.alexanderp.chesser.mainUI.navigation.UIActionNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class MainUIModule {
    @Module
    @InstallIn(ActivityComponent::class)
    object NavControllerModule {
        @Provides
        fun navController(activity: FragmentActivity): NavController {
            val fragment = activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!
            return NavHostFragment.findNavController(fragment)
        }
    }

    @Module
    @InstallIn(ActivityComponent::class)
    abstract class HomeModule {
        @Binds
        abstract fun homeNavigationActions(uiActionNavigator: UIActionNavigator): HomeNavigationActions
    }
}
