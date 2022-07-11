package com.example.favoritefilmsactors.presentation.recviev

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.favoritefilmsactors.R
import com.example.favoritefilmsactors.databinding.SingleItemMovieOnWishlistBinding
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.utils.constance.Constance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieWishListAdapter @Inject constructor() :
    ListAdapter<MovieSimple, MovieWishListAdapter.MovieRecVievWishListHolder>(
        MovieDiffUtilListAdapter()
    ) {

    inner class MovieRecVievWishListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = SingleItemMovieOnWishlistBinding.bind(itemView)

        fun bindAll(){
            val currentItem = getItem(adapterPosition)
            val currentUriImg = Constance.BASE_PATH_IMAGE + currentItem.posterPath
            binding.tvTitleWishlist.text = currentItem.title
            binding.tvDescOnWishlist.text = currentItem.overview
            CoroutineScope(Dispatchers.IO).launch {
                binding.imgOnWishlist.load(currentUriImg) {
                    // TRY to delete
                    placeholder(R.drawable.ic_baseline_downloading_24)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRecVievWishListHolder {
        LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item_movie_on_wishlist, parent, false).also {
            return MovieRecVievWishListHolder(it)
        }
    }

    override fun onBindViewHolder(holder: MovieRecVievWishListHolder, position: Int) {
        holder.bindAll()
    }
}