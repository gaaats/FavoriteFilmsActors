package com.example.favoritefilmsactors.presentation.recviev

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.favoritefilmsactors.R
import com.example.favoritefilmsactors.domain.entity.MovieSimple

class MovieListAdapter :
    ListAdapter<MovieSimple, MovieRecVievVievHolder>(MovieDiffUtilListAdapter()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRecVievVievHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.single_item_movie, parent, false)
            .also {
                return MovieRecVievVievHolder(it)
            }
    }

    override fun onBindViewHolder(holder: MovieRecVievVievHolder, position: Int) {
        val currentMovie = getItem(position)
        holder.binding.apply {
            tvTitle.text = currentMovie.title?: "default"
            tvDescription.text = currentMovie.overview
        }
    }
}