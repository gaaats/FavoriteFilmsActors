package com.example.favoritefilmsactors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.favoritefilmsactors.databinding.FragmentFavoriteMoviesBinding
import com.example.favoritefilmsactors.databinding.FragmentMoviesListBinding

class FavoriteMoviesFragment : Fragment() {

    private var _binding: FragmentFavoriteMoviesBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentFavoriteMoviesBinding is null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
/*
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteMoviesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
 */
}