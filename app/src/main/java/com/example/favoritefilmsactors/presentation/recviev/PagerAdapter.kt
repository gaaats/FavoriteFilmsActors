package com.example.favoritefilmsactors.presentation.recviev

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.favoritefilmsactors.R
import com.example.favoritefilmsactors.data.room.entity.images.ImagesItem
import com.example.favoritefilmsactors.databinding.ItemVievPagerBinding
import com.example.favoritefilmsactors.utils.constance.Constance
import javax.inject.Inject

class PagerAdapter @Inject constructor() :
    ListAdapter<ImagesItem, PagerAdapter.PagerImagesRecVVievHolder>(PagerImagesDiffUtilListAdapter()) {

    inner class PagerImagesRecVVievHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemVievPagerBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerImagesRecVVievHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_viev_pager, parent, false).also {
            return PagerImagesRecVVievHolder(it)
        }

    }

    override fun onBindViewHolder(holder: PagerImagesRecVVievHolder, position: Int) {

        val currentImage = getItem(position)
        Log.d(Constance.TAG, "path inside ADAPTER: ${currentImage.filePath}")
        holder.binding.imageViev.load(currentImage.filePath) {
            placeholder(R.drawable.ic_baseline_downloading_24)
//                    crossfade(true)
//                    crossfade(200)
        }
    }
}