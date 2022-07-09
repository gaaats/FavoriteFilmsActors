package com.example.favoritefilmsactors.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.favoritefilmsactors.R
import com.example.favoritefilmsactors.databinding.FragmentHomeBinding
import com.example.favoritefilmsactors.databinding.FragmentMoviesListBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding ?: throw RuntimeException("FragmentHomeBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnMovie.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_fragmentMoviesList)
        }
        binding.btnActors.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_actorsFragment)
        }
        binding.btnTvShows.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_tvShovFragment)
            findNavController().navigate(R.id.action_homeFragment_to_pagerFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle()
            }
    }
}