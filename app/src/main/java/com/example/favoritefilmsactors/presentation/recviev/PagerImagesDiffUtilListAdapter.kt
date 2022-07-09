package com.example.favoritefilmsactors.presentation.recviev

import androidx.recyclerview.widget.DiffUtil
import com.example.favoritefilmsactors.data.room.entity.images.ImagesItem

class PagerImagesDiffUtilListAdapter: DiffUtil.ItemCallback<ImagesItem>() {
    override fun areItemsTheSame(oldItem: ImagesItem, newItem: ImagesItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImagesItem, newItem: ImagesItem): Boolean {
        return oldItem == newItem
    }
}