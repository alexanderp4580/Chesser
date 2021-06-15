package com.alexanderp.chesser.mainUI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.alexanderp.chesser.mainUI.databinding.ActivityChesserBinding
import dagger.hilt.android.AndroidEntryPoint
import mu.KotlinLogging

@AndroidEntryPoint
open class ChesserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChesserBinding

    private val logger = KotlinLogging.logger {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChesserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
