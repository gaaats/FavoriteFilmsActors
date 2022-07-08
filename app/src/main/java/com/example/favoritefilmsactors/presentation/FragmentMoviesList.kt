package com.example.favoritefilmsactors.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.favoritefilmsactors.R
import com.example.favoritefilmsactors.data.remote.api.TMDBService
import com.example.favoritefilmsactors.data.room.TMDbDataBase
import com.example.favoritefilmsactors.databinding.FragmentMoviesListBinding
import com.example.favoritefilmsactors.presentation.recviev.MovieListAdapter
import com.example.favoritefilmsactors.presentation.vievmodels.MovieVievModel
import com.example.favoritefilmsactors.presentation.vievmodels.MovieVievModelFactory
import com.example.favoritefilmsactors.utils.constance.Constance
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class FragmentMoviesList : Fragment() {

    @Inject
    lateinit var vievModelfactory: MovieVievModelFactory

    @Inject
    lateinit var movieAdapter : MovieListAdapter

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
//        val movieAdapter = MovieListAdapter()
        binding.recVievPlaceHolder.adapter = movieAdapter

//        val retrofit = Retrofit.Builder()
//            .baseUrl(TMDBService.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()

//        val service = retrofit.create(TMDBService::class.java)
//        getListFromNetvork(service)

        collectFlovAndRepeatOnLifeCycle(movieVievModel.getFlovMovies) {
            movieAdapter.submitList(it)
        }

        movieVievModel.loading.observe(viewLifecycleOwner) {
            if (it) binding.progBar.visibility = View.VISIBLE
            else binding.progBar.visibility = View.GONE
        }



        super.onViewCreated(view, savedInstanceState)
    }

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