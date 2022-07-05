package com.example.favoritefilmsactors.presentation.recviev

import androidx.recyclerview.widget.DiffUtil
import com.example.favoritefilmsactors.domain.entity.MovieSimple

class MovieDiffUtilListAdapter: DiffUtil.ItemCallback<MovieSimple>() {
    override fun areItemsTheSame(oldItem: MovieSimple, newItem: MovieSimple): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieSimple, newItem: MovieSimple): Boolean {
        return oldItem == newItem
    }
}