package com.example.favoritefilmsactors.presentation.recviev

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.example.favoritefilmsactors.R
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.utils.constance.Constance
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListAdapter :
    ListAdapter<MovieSimple, MovieRecVievVievHolder>(MovieDiffUtilListAdapter()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRecVievVievHolder {
        var count = 1
        LayoutInflater.from(parent.context).inflate(R.layout.single_item_movie, parent, false)
            .also {
                return MovieRecVievVievHolder(it)
            }
    }

    override fun onBindViewHolder(holder: MovieRecVievVievHolder, position: Int) {
        val currentMovie = getItem(position)
        val currentUriImg = Constance.BASE_PATH_IMAGE+currentMovie.posterPath
        holder.binding.apply {
            tvTitle.text = currentMovie.title?: "default"
            tvDescription.text = currentMovie.overview
            tvMark.text = currentMovie.releaseDate
            CoroutineScope(Dispatchers.IO).launch {
                img.load(currentUriImg){
                    placeholder(R.drawable.ic_baseline_downloading_24)
//                    crossfade(true)
//                    crossfade(200)
                }
            }
            cardViev.setOnClickListener{
                Snackbar.ANIMATION_MODE_SLIDE
                Snackbar.make(
                    it, "You pressed: ${currentMovie.title}", Snackbar.LENGTH_SHORT
                ).show()
//                Toast.makeText(it.context, "You pressed: ${currentMovie.title}", Toast.LENGTH_SHORT).show()
            }

        }
    }
}