package com.alexanderp.chesser.homeUI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alexanderp.chesser.homeUI.databinding.FragmentHomeBinding
import com.alexanderp.chesser.homeUI.navigation.HomeNavigationActions
import com.alexanderp.chesser.homeUI.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import mu.KotlinLogging
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var homeNavigationActions: HomeNavigationActions

    private val logger = KotlinLogging.logger {}
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater)

        binding.homeTimerButton.setOnClickListener {
            logger.debug { "onClick" }
            homeViewModel.onClick()
            homeNavigationActions.navigateToTimerSettings("testing")
        }

        return binding.root
    }
}