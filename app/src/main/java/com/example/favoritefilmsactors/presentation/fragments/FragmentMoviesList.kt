package com.example.favoritefilmsactors.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.favoritefilmsactors.databinding.FragmentMoviesListBinding
import com.example.favoritefilmsactors.domain.entity.MovieSimple
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
        Log.d("currentstate", "onCreateView")
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)

        initLoadingPopularMoviesOnMainScreen()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("currentstate", "onViewCreated")

        initAdapterForRecViev()
        initProgBar()
        initFunAddToWishlist()
        initNavigationForMoreImages()
        createSearchByNameMovie()

        movieVievModel.statusMessage.observe(viewLifecycleOwner) {
            Snackbar.make(this.view!!, it.peekContent(), Snackbar.LENGTH_SHORT).show()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        // make it for normal updating after deleting movies from favorite
        movieAdapter.onDetachedFromRecyclerView(binding.recVievPlaceHolder)
        _binding = null
        Log.d("currentstate", "onDestroyView")
        super.onDestroyView()
    }

    private fun initLoadingPopularMoviesOnMainScreen() {
        collectFlovFragment(movieVievModel.getFlovMovies) {
            movieAdapter.submitData(it)
        }
    }

    private fun initAdapterForRecViev() {
        binding.recVievPlaceHolder.adapter = movieAdapter
    }

    private fun createSearchByNameMovie() {

        binding.searchViev.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                initSearchInsideSearchViev(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
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
        if (query?.isNotEmpty() == true && query.isNotBlank()) {
            binding.recVievPlaceHolder.alpha = 0.1F
            collectFlovFragment(movieVievModel.filmFlow) {
                movieVievModel.changeCurrentQueryFromInputFlov(query)
                binding.recVievPlaceHolder.alpha = 1F
                movieAdapter.submitData(it)
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
        movieAdapter.checkMovieIsFavoriteOrNot = {
            movieVievModel.checkMovieIsFavoriteOrNot(it)
        }
    }

    private fun initFunAddToWishlist() {
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
                } else {
                    recVievPlaceHolder.visibility = View.VISIBLE
                    searchViev.visibility = View.VISIBLE
                    progBar.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        private const val MIN_TIME_FOR_LOADING: Long = 1
    }
}

fun <T> Fragment.collectFlovFragment(
    flow: Flow<T>,
    functionSuspend: suspend (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(functionSuspend)
        }
    }
}

fun <T> AppCompatActivity.collectFlovActivity(
    flow: Flow<T>,
    functionSuspend: suspend (T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(functionSuspend)
        }
    }
}


