package com.example.favoritefilmsactors.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.favoritefilmsactors.R
import com.example.favoritefilmsactors.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}