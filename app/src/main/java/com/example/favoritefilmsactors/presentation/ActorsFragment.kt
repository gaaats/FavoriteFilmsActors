package com.example.favoritefilmsactors.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.favoritefilmsactors.R
import com.example.favoritefilmsactors.databinding.FragmentActorsBinding
import com.example.favoritefilmsactors.databinding.FragmentHomeBinding

class ActorsFragment : Fragment() {
    private var _binding: FragmentActorsBinding? = null
    val binding get() = _binding ?: throw RuntimeException("FragmentActorsBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActorsBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_actors, container, false)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ActorsFragment().apply {
                arguments = Bundle()
            }
    }
}