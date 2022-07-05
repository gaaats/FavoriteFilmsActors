package com.example.favoritefilmsactors.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.favoritefilmsactors.R
import com.example.favoritefilmsactors.databinding.FragmentMoviesListBinding

class FragmentMoviesList : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    val binding get() = _binding ?: throw RuntimeException("FragmentMoviesListBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        fun generateFragmentMoviesList(): FragmentMoviesList {
            return FragmentMoviesList().apply {
                arguments = Bundle()
            }
        }
    }
}