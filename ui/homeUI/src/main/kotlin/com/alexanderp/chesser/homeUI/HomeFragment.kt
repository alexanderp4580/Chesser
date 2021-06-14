package com.alexanderp.chesser.homeUI

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alexanderp.chesser.common.ui.viewBinding
import com.alexanderp.chesser.homeUI.databinding.FragmentHomeBinding
import com.alexanderp.chesser.homeUI.navigation.HomeNavigationActions
import dagger.hilt.android.AndroidEntryPoint
import mu.KotlinLogging
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding()

    @Inject
    lateinit var homeNavigationActions: HomeNavigationActions

    private val logger = KotlinLogging.logger {}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.homeMenuTimerButton.setOnClickListener {
            logger.debug { "homeMenuTimerButton clicked" }
            homeNavigationActions.navigateToTimerSettings()
        }
    }
}