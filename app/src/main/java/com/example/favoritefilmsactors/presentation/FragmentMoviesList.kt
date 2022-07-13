package com.example.favoritefilmsactors.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.get
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.favoritefilmsactors.databinding.FragmentMoviesListBinding
import com.example.favoritefilmsactors.presentation.recviev.MovieListAdapter
import com.example.favoritefilmsactors.presentation.vievmodels.MovieVievModel
import com.example.favoritefilmsactors.presentation.vievmodels.MovieVievModelFactory
import com.example.favoritefilmsactors.utils.constance.Constance
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@AndroidEntryPoint
class FragmentMoviesList : Fragment() {

    @Inject
    lateinit var vievModelfactory: MovieVievModelFactory

    @Inject
    lateinit var movieAdapter: MovieListAdapter
    val movieVievModel by lazy {
        ViewModelProvider(this, vievModelfactory)[MovieVievModel::class.java]
    }

    private var _binding: FragmentMoviesListBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentMoviesListBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initLoadingPopularMoviesOnMainScreen()
        initProgBar()

        CoroutineScope(Dispatchers.IO).launch {
            initFunAddToWishlist()
        }
        initNavigationForMoreImages()
        createSearchByNameMovie()

        movieVievModel.statusMessage.observe(viewLifecycleOwner){
            Snackbar.make(this.view!!, it.peekContent(), Snackbar.LENGTH_SHORT).show()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initLoadingPopularMoviesOnMainScreen() {
        binding.recVievPlaceHolder.adapter = movieAdapter
        collectFlovAndRepeatOnLifeCycle(movieVievModel.getFlovMovies) {
            movieAdapter.submitData(it)
        }
    }

    private fun createSearchByNameMovie() {

        binding.searchViev.setOnSearchClickListener {
            binding.recVievPlaceHolder.alpha = 0.1F
        }

        binding.searchViev.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                initSearchInsideSearchViev(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.recVievPlaceHolder.alpha = 0.1F
//                initSearchInsideSearchViev(newText, true)
                return false
            }
        })
        binding.searchViev.setOnCloseListener {
            initLoadingPopularMoviesOnMainScreen()
            binding.recVievPlaceHolder.alpha = 1F
            false
        }
    }

    private fun initSearchInsideSearchViev(query: String?) {
        // todo make after paging
        if (query?.isNotEmpty() == true && query.isNotBlank()) {
            CoroutineScope(Dispatchers.Main).launch {
                movieVievModel.changeCurrentQueryFromInputFlov(query!!)
                collectFlovAndRepeatOnLifeCycle(movieVievModel.filmFlow) {
                    CoroutineScope(Dispatchers.Main).launch{
                        binding.recVievPlaceHolder.alpha = 1F
                        movieAdapter.submitData(it)
                    }
                }
            }
        }

    }

    private fun initNavigationForMoreImages() {
        movieAdapter.navigateMoreImages = {
            FragmentMoviesListDirections.actionFragmentMoviesListToPagerFragment(it).also {
                findNavController().navigate(it)
            }
            Log.d(Constance.TAG, "movieAdapter.navigate")
        }
    }

    private suspend fun initFunAddToWishlist() {
        movieAdapter.addToWishlist = {
            CoroutineScope(Dispatchers.IO).launch {
                movieVievModel.addSingleMovieToWishlist(it)
            }
        }
    }

    private fun initProgBar() {
        movieVievModel.loading.observe(viewLifecycleOwner) {
            binding.apply {
                if (it) {
                    recVievPlaceHolder.visibility = View.INVISIBLE
                    searchViev.visibility = View.INVISIBLE
                    progBar.visibility = View.VISIBLE
                }  else{
                    recVievPlaceHolder.visibility = View.VISIBLE
                    searchViev.visibility = View.VISIBLE
                    progBar.visibility = View.GONE

                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        private const val MIN_TIME_FOR_LOADING: Long = 1
    }
}

fun <T> Fragment.collectFlovAndRepeatOnLifeCycle(
    flow: Flow<T>,
    functionSuspend: suspend (T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(functionSuspend)
        }
    }
}