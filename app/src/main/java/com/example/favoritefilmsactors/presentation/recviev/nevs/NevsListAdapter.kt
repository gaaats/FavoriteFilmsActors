package com.example.favoritefilmsactors.presentation.recviev.nevs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.EventListener
import coil.load
import com.example.favoritefilmsactors.R
import com.example.favoritefilmsactors.databinding.NevsSingleItemBinding
import com.example.favoritefilmsactors.databinding.SingleItemMovieOnWishlistBinding
import com.example.favoritefilmsactors.domain.entity.ArticleUIModel
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.utils.constance.Constance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NevsListAdapter @Inject constructor() :
    ListAdapter<ArticleUIModel, NevsListAdapter.NevsRecVievHolder>(
        NevsDiffUtilListAdapter()
    ) {

    private var onItemNevsClickListener : ((article: ArticleUIModel) -> Unit)? = null

    inner class NevsRecVievHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = NevsSingleItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NevsRecVievHolder {
        LayoutInflater.from(parent.context)
            .inflate(R.layout.nevs_single_item, parent, false).also {
                return NevsRecVievHolder(it)
            }
    }

    override fun onBindViewHolder(holder: NevsRecVievHolder, position: Int) {
        val currentItem = getItem(position)
        val currentUriImg = currentItem.urlToImage


        holder.binding.apply {
            tvTitle.text = currentItem.title
            tvDescription.text = currentItem.description
            tvSource.text = currentItem.source
            tvPublishedAt.text = currentItem.publishedAt

            if (currentUriImg == "no") {
                ivArticleImage.setImageResource(R.drawable.news)
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    ivArticleImage.load(currentUriImg) {
                        placeholder(R.drawable.ic_baseline_downloading_24)
                    }
                }
            }
            root.setOnClickListener {
                onItemNevsClickListener?.invoke(currentItem)
            }
        }
    }
    fun setOnItemClickListener (listener: (article: ArticleUIModel)-> Unit){
        onItemNevsClickListener = listener
    }
}