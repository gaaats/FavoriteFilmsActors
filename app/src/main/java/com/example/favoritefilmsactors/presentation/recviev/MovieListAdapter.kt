package com.example.favoritefilmsactors.presentation.recviev

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.example.favoritefilmsactors.R
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.presentation.vievmodels.MovieVievModel
import com.example.favoritefilmsactors.presentation.vievmodels.MovieVievModelFactory
import com.example.favoritefilmsactors.utils.constance.Constance
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random


class MovieListAdapter @Inject constructor(

) :
    PagingDataAdapter<MovieSimple, MovieRecVievVievHolder>(MovieDiffUtilListAdapter()) {

    var checkMovieIsFavoriteOrNot: ((movie: MovieSimple) -> Boolean)? = null
    var navigateMoreImages: ((id: Int) -> Unit)? = null
    var addToWishlist: ((movie: MovieSimple) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRecVievVievHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.single_item_movie, parent, false)
            .also {
                return MovieRecVievVievHolder(it)
            }
    }

    override fun onBindViewHolder(holder: MovieRecVievVievHolder, position: Int) {

        if (Random.nextBoolean()) {
            holder.binding.star5.visibility = View.INVISIBLE
        } else holder.binding.star5.visibility = View.VISIBLE

        val currentMovie =
            getItem(position) ?: throw RuntimeException("currentMovie in NULL - MovieListAdapter")
        val currentUriImg = Constance.BASE_PATH_IMAGE + currentMovie.posterPath
        holder.binding.apply {

            imgAddToWishList.setImageResource(R.drawable.empty_heart_fav)
            if (checkMovieIsFavoriteOrNot?.invoke(currentMovie) == true) {
                imgAddToWishList.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
            tvTitle.text = currentMovie.title ?: "default"
            tvDescription.text = currentMovie.overview
            tvMark.text = currentMovie.releaseDate
            CoroutineScope(Dispatchers.IO).launch {
                img.load(currentUriImg) {
                    placeholder(R.drawable.ic_baseline_downloading_24)
                }
            }
            cardViev.setOnClickListener {
                navigateToPagerFragment(currentMovie)
            }
//            imgAddToWishList.setOnClickListener {
//                Log.d(Constance.TAG, "imgAddToWishList setOnClickListener :${currentMovie.id}")
//                addToWishlistImpl(currentMovie)
//            }
            imgAddToWishList.setOnLongClickListener() {
                Log.d(Constance.TAG, "imgAddToWishList setOnLongClickListener :${currentMovie.id}")
                addToWishlistImpl(currentMovie)
                imgAddToWishList.setImageResource(R.drawable.ic_baseline_favorite_24)
                Snackbar.make(it, "movie successfully add to Wishlist", Snackbar.LENGTH_SHORT)
                    .show()
                true
            }
        }
    }

    private fun navigateToPagerFragment(currentMovie: MovieSimple) {
        navigateMoreImages?.invoke(currentMovie.id)
    }

    private fun addToWishlistImpl(currentMovie: MovieSimple) {
        addToWishlist?.invoke(currentMovie)
    }


/*
// NORMAL - работает
class MovieListAdapter @Inject constructor(
) :
    ListAdapter<MovieSimple, MovieRecVievVievHolder>(MovieDiffUtilListAdapter()) {

    var navigateMoreImages: ((id: Int) -> Unit)? = null
    var addToWishlist: ((movie: MovieSimple) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRecVievVievHolder {
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
            imgAddToWishList.setImageResource(R.drawable.empty_heart_fav)
            tvTitle.text = currentMovie.title ?: "default"
            tvDescription.text = currentMovie.overview
            tvMark.text = currentMovie.releaseDate
            CoroutineScope(Dispatchers.IO).launch {
                img.load(currentUriImg) {
                    placeholder(R.drawable.ic_baseline_downloading_24)
                }
            }
            cardViev.setOnClickListener {
                navigateToPagerFragment(currentMovie)
            }
//            imgAddToWishList.setOnClickListener {
//                Log.d(Constance.TAG, "imgAddToWishList setOnClickListener :${currentMovie.id}")
//                addToWishlistImpl(currentMovie)
//            }
            imgAddToWishList.setOnLongClickListener() {
                Log.d(Constance.TAG, "imgAddToWishList setOnLongClickListener :${currentMovie.id}")
                addToWishlistImpl(currentMovie)
                Snackbar.make(it, "movie successfully add to Wishlist", Snackbar.LENGTH_SHORT).show()
                true
            }
        }
    }

    private fun navigateToPagerFragment(currentMovie: MovieSimple) {
        navigateMoreImages?.invoke(currentMovie.id)
    }

    private fun addToWishlistImpl(currentMovie: MovieSimple) {
        addToWishlist?.invoke(currentMovie)
    }
    */

}
