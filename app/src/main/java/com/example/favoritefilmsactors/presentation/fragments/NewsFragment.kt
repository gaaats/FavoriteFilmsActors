package com.example.favoritefilmsactors.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.favoritefilmsactors.databinding.FragmentFavoriteMoviesBinding
import com.example.favoritefilmsactors.databinding.FragmentNewsBinding
import com.example.favoritefilmsactors.presentation.recviev.MovieListAdapter
import com.example.favoritefilmsactors.presentation.recviev.nevs.NevsListAdapter
import com.example.favoritefilmsactors.presentation.vievmodels.MovieVievModel
import com.example.favoritefilmsactors.presentation.vievmodels.MovieVievModelFactory
import com.example.favoritefilmsactors.presentation.vievmodels.NevsVievModelFactory
import com.example.favoritefilmsactors.presentation.vievmodels.NewsViewModel
import com.example.favoritefilmsactors.utils.ApiStatus
import com.example.favoritefilmsactors.utils.CurrentResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : Fragment() {

    @Inject
    lateinit var vievModelfactory: NevsVievModelFactory
    @Inject
    lateinit var nevsAdapter: NevsListAdapter

    val nevsVievModel by lazy {
        ViewModelProvider(this, vievModelfactory)[NewsViewModel::class.java]
    }

    private var _binding: FragmentNewsBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentNewsBinding is null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvNews.adapter = nevsAdapter
        collectFlovAndRepeatOnLifeCycle(nevsVievModel.nevsFlov){
            when(it.status){
                ApiStatus.SUCCESS -> {
                    nevsAdapter.submitList(it.getNotNulldata())
                    Log.d("Test55", "good")
                }
                ApiStatus.LOADING -> {
                    Log.d("Test55", "LOADING")
                }
                ApiStatus.ERROR -> {
                    Log.d("Test55", "ERROR + ${it.message.toString()}")
                }
                ApiStatus.EMPTY -> {
                    Log.d("Test55", "EMPTY")
                }

            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
