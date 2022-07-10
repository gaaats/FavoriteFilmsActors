package com.example.favoritefilmsactors.presentation

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
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.favoritefilmsactors.R
import com.example.favoritefilmsactors.data.remote.api.TMDBService
import com.example.favoritefilmsactors.data.room.TMDbDataBase
import com.example.favoritefilmsactors.databinding.FragmentMoviesListBinding
import com.example.favoritefilmsactors.presentation.recviev.MovieListAdapter
import com.example.favoritefilmsactors.presentation.vievmodels.MovieVievModel
import com.example.favoritefilmsactors.presentation.vievmodels.MovieVievModelFactory
import com.example.favoritefilmsactors.utils.constance.Constance
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import okhttp3.internal.wait
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recVievPlaceHolder.adapter = movieAdapter

/*
        val retrofit = Retrofit.Builder()
            .baseUrl(TMDBService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(TMDBService::class.java)
        */

        initLoadingPopularMoviesOnMainScreen()
        initProgBar()
        initNavigationForMoreImages()
        createSearchByNameMovie()

        /*
//        binding.searchViev.setOnClickListener {
//            /* TEST
//            CoroutineScope(Dispatchers.IO).launch {
//                val result = service.getSearchedMoviesByName(query = "паук")
//                Log.d(Constance.TAG, "result is: ${result.code()}")
//                result.body()!!.movies.forEach {
//                    Log.d(Constance.TAG, "result is: ${it.title}")
//                }
//            }        */
//            CoroutineScope(Dispatchers.IO).launch {
//                movieVievModel.searchMovieByName("человек паук")
//                delay(2000)
//                withContext(Dispatchers.Main) {
//                    movieVievModel.listMovies.observe(viewLifecycleOwner){
//                        movieAdapter.submitList(it)
//                    }
//                }
//            }
//        }
         */
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initLoadingPopularMoviesOnMainScreen() {
        collectFlovAndRepeatOnLifeCycle(movieVievModel.getFlovMovies) {
            movieAdapter.submitList(it)
        }
    }

    private fun createSearchByNameMovie() {

        binding.searchViev.setOnSearchClickListener {
            binding.recVievPlaceHolder.alpha = 0.1F
        }

        binding.searchViev.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                initSearchInsideSearchViev(query, false)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
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

    private fun initSearchInsideSearchViev(query: String?, needDelay: Boolean = false) {
        binding.recVievPlaceHolder.alpha = 0.1F
        CoroutineScope(Dispatchers.IO).launch {
            if (needDelay) delay(3000)
            binding.recVievPlaceHolder.alpha = 1F
            if (query?.isNotEmpty() == true && query.isNotBlank()) {
                movieVievModel.searchMovieByName(query)
                withContext(Dispatchers.Main) {
                    movieVievModel.listMovies.observe(viewLifecycleOwner) {
                        movieAdapter.submitList(it)
                    }
                }
            }
        }
    }

    private fun initNavigationForMoreImages() {
        movieAdapter.navigate = {
            FragmentMoviesListDirections.actionFragmentMoviesListToPagerFragment(it).also {
                findNavController().navigate(it)
            }
            Log.d(Constance.TAG, "movieAdapter.navigate")
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
/*
//    private fun getListFromNetvork(service: TMDBService) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val result = service.getPopularMovies()
//            if (result.isSuccessful){
//                Log.d(Constance.TAG, "good")
//                for (elements in result.body()!!.movies){
//                    Log.d(Constance.TAG, "element ${elements.title}")
//                }
//
//            } else{
//                Log.d(Constance.TAG, "bed")
//                Log.d(Constance.TAG, "bed ${result.errorBody()}")
//                Log.d(Constance.TAG, "bed ${result.code()}")
//            }
//        }
//    }

 */

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    /*
    companion object {
        fun generateFragmentMoviesList(): FragmentMoviesList {
            return FragmentMoviesList().apply {
                arguments = Bundle()
            }
        }

        fun openPager() {
            NavHostFragment.findNavController(fragment = FragmentMoviesList())
                .navigate(R.id.action_fragmentMoviesList_to_pagerFragment)
        }
    }
     */

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