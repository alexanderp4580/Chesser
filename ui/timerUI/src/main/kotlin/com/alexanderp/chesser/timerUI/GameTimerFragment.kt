package com.alexanderp.chesser.timerUI

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.alexanderp.chesser.common.models.ActivePlayer
import com.alexanderp.chesser.common.models.GameTime
import com.alexanderp.chesser.common.models.GameTimerState
import com.alexanderp.chesser.common.ui.viewBinding
import com.alexanderp.chesser.timerUI.databinding.FragmentGameTimerBinding
import com.alexanderp.chesser.timerUI.ktx.disableFullScreen
import com.alexanderp.chesser.timerUI.ktx.enableFullScreen
import com.alexanderp.chesser.timerUI.ktx.formatChessTime
import com.alexanderp.chesser.timerUI.viewmodels.GameTimerViewModel
import dagger.hilt.android.AndroidEntryPoint
import mu.KotlinLogging
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
@OptIn(ExperimentalTime::class)
class GameTimerFragment : Fragment(R.layout.fragment_game_timer) {
    private val binding: FragmentGameTimerBinding by viewBinding()
    private val viewModel: GameTimerViewModel by viewModels()
    private val logger = KotlinLogging.logger {}
    private val gameTimerFragmentArgs: GameTimerFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.enableFullScreen()

        setupBinding()
        viewModel.startGame(gameTimerFragmentArgs.timerConfig)
        viewModel.gameTimerState.observe(viewLifecycleOwner, this::renderGameState)
    }

    private fun setupBinding() {
        with(binding) {
            playerOneContainer.setOnClickListener { viewModel.onTimerButtonPressed(ActivePlayer.PlayerTwo) }
            playerTwoContainer.setOnClickListener { viewModel.onTimerButtonPressed(ActivePlayer.PlayerOne) }
            timerRestartButton.setOnClickListener { viewModel.startGame(gameTimerFragmentArgs.timerConfig) }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun renderGameState(gameTimerState: GameTimerState) {
        logger.info { "Rendering game timer state: $gameTimerState" }
        with(binding) {
            when (gameTimerState) {
                is GameTimerState.Ready -> {
                    renderTime(gameTimerState.gameTime)
                    renderPreGameState()
                }
                is GameTimerState.Playing -> {
                    renderTime(gameTimerState.gameTime)
                    if (gameTimerState.activePlayer == ActivePlayer.PlayerOne) {
                        renderPlayOneTurn()
                    } else {
                        renderPlayTwoTurn()
                    }
                }
                is GameTimerState.Ended -> {
                    renderTime(gameTimerState.gameTime)
                    renderEndGameState()
                }
                else -> {
                    logger.error { "Unknown game state: $gameTimerState" }
                }
            }
        }
    }

    private fun FragmentGameTimerBinding.renderTime(gameTime: GameTime) {
        playerOneTime.text = gameTime.playerOneTime.formatChessTime()
        playerTwoTime.text = gameTime.playerTwoTime.formatChessTime()
    }

    private fun FragmentGameTimerBinding.renderPlayTwoTurn() {
        playerTwoTime.isEnabled = true
        playerOneTime.isEnabled = false
        playerTwoContainer.isEnabled = true
        playerTwoContainer.isSelected = true
        playerOneContainer.isEnabled = false
    }

    private fun FragmentGameTimerBinding.renderPlayOneTurn() {
        playerOneTime.isEnabled = true
        playerTwoTime.isEnabled = false
        playerOneContainer.isEnabled = true
        playerOneContainer.isSelected = true
        playerTwoContainer.isEnabled = false
    }

    private fun FragmentGameTimerBinding.renderPreGameState() {
        playerOneTime.isEnabled = true
        playerTwoTime.isEnabled = true
        playerOneContainer.isEnabled = true
        playerTwoContainer.isEnabled = true
        playerTwoContainer.isSelected = false
        playerOneContainer.isSelected = false
        timerRestartButton.visibility = View.GONE
    }

    private fun FragmentGameTimerBinding.renderEndGameState() {
        playerOneTime.isEnabled = false
        playerTwoTime.isEnabled = false
        playerOneContainer.isEnabled = false
        playerTwoContainer.isEnabled = false
        playerTwoContainer.isSelected = false
        playerOneContainer.isSelected = false
        timerRestartButton.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window.disableFullScreen()
    }
}
