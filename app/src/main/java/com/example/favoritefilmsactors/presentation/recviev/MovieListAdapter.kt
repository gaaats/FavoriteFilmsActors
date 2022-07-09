package com.example.favoritefilmsactors.presentation.recviev

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.example.favoritefilmsactors.R
import com.example.favoritefilmsactors.data.remote.api.TMDBService
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.presentation.FragmentMoviesList
import com.example.favoritefilmsactors.presentation.vievmodels.MovieVievModel
import com.example.favoritefilmsactors.presentation.vievmodels.MovieVievModelFactory
import com.example.favoritefilmsactors.utils.constance.Constance
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class MovieListAdapter @Inject constructor(
    private val apiService: TMDBService
) :
    ListAdapter<MovieSimple, MovieRecVievVievHolder>(MovieDiffUtilListAdapter()) {


    var navigate: ((id: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRecVievVievHolder {
        var count = 1
        LayoutInflater.from(parent.context).inflate(R.layout.single_item_movie, parent, false)
            .also {
                return MovieRecVievVievHolder(it)
            }
    }

    override fun onBindViewHolder(holder: MovieRecVievVievHolder, position: Int) {
        if (Random.nextBoolean()) {
            holder.binding.star5.visibility = View.INVISIBLE
        } else holder.binding.star5.visibility = View.VISIBLE

        val currentMovie = getItem(position)
        val currentUriImg = Constance.BASE_PATH_IMAGE + currentMovie.posterPath
        holder.binding.apply {
            tvTitle.text = currentMovie.title ?: "default"
            tvDescription.text = currentMovie.overview
            tvMark.text = currentMovie.releaseDate
            CoroutineScope(Dispatchers.IO).launch {
                img.load(currentUriImg) {
                    placeholder(R.drawable.ic_baseline_downloading_24)
//                    crossfade(true)
//                    crossfade(200)
                }
            }
            cardViev.setOnClickListener {
                navigateToPagerFragment(currentMovie)
                /*
                Snackbar.ANIMATION_MODE_SLIDE
                Snackbar.make(
                    it, "You pressed: ${currentMovie.title}", Snackbar.LENGTH_SHORT
                ).show()
                 */
//                CoroutineScope(Dispatchers.IO).launch {
//                    val images = apiService.getMoviesImages(movieId = currentMovie.id)
//                    if (images.isSuccessful) {
//                        Log.d(Constance.TAG, "good load images")
//                        images?.body()?.posters?.let {
//                            it.forEach { posterItem ->
//                                Log.d(Constance.TAG, "path is: ${posterItem.filePath}")
//                            }
//                        }
//                    } else {
//                        Log.d(Constance.TAG, "ERROR in load images")
//                    }
//                }

            }
        }
    }

    private fun navigateToPagerFragment(currentMovie: MovieSimple) {
        navigate?.invoke(currentMovie.id)
    }
}