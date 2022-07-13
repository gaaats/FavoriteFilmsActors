package com.example.favoritefilmsactors.presentation.recviev.nevs

import androidx.recyclerview.widget.DiffUtil
import com.example.favoritefilmsactors.domain.entity.ArticleUIModel
import com.example.favoritefilmsactors.domain.entity.MovieSimple

class NevsDiffUtilListAdapter: DiffUtil.ItemCallback<ArticleUIModel>() {
    override fun areItemsTheSame(oldItem: ArticleUIModel, newItem: ArticleUIModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ArticleUIModel, newItem: ArticleUIModel): Boolean {
        return oldItem == newItem
    }

}