package com.alexanderp.chesser

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alexanderp.chesser.databinding.ActivityMainBinding
import mu.KotlinLogging

/**
 * Some Dummy text.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val logger = KotlinLogging.logger {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonCompute.setOnClickListener {
            val input = binding.editTextFactorial.text.toString().toInt()

            binding.textResult.text = input.toString()
            binding.textResult.visibility = View.VISIBLE
        }
    }
}
