package com.example.favoritefilmsactors.presentation.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.favoritefilmsactors.databinding.FragmentFavoriteMoviesBinding
import com.example.favoritefilmsactors.presentation.recviev.MovieWishListAdapter
import com.example.favoritefilmsactors.presentation.vievmodels.MovieVievModel
import com.example.favoritefilmsactors.presentation.vievmodels.MovieVievModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment() {

    @Inject
    lateinit var vievModelfactory: MovieVievModelFactory

    @Inject
    lateinit var movieWishListAdapter: MovieWishListAdapter
    val movieVievModel by lazy {
        ViewModelProvider(this, vievModelfactory)[MovieVievModel::class.java]
    }

    private var _binding: FragmentFavoriteMoviesBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentFavoriteMoviesBinding is null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initAdapterAndLoadMoviesWishlist()
        createMethodSwipeAndDelete()

        checkListEmptyOrNotMakeVisible()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun checkListEmptyOrNotMakeVisible() {
        movieVievModel.wishlistIsEmptyOrNull.observe(viewLifecycleOwner) {
            if (it) {
                binding.recVievPlaceHolderOnWishlist.visibility = View.GONE
                binding.textViewNoFavMovies.visibility = View.VISIBLE
                binding.imgNoFavoritesMovies.visibility = View.VISIBLE
            } else {
                binding.recVievPlaceHolderOnWishlist.visibility = View.VISIBLE
                binding.textViewNoFavMovies.visibility = View.GONE
                binding.imgNoFavoritesMovies.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun initAdapterAndLoadMoviesWishlist() {
        initProgBar()
        lifecycleScope.launch {
            movieVievModel.loadFavoriteMovies()
//            checkListEmptyOrNotMakeVisible()
        }
        binding.recVievPlaceHolderOnWishlist.adapter = movieWishListAdapter
        movieVievModel.listFavoriteMovies.observe(viewLifecycleOwner) {
            movieWishListAdapter.submitList(it)
        }
    }


    private fun createMethodSwipeAndDelete() {
        val callBack = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemSwiped = movieWishListAdapter.currentList[viewHolder.adapterPosition]
                AlertDialog.Builder(activity)
                    .setTitle("DELETE MOVIE FROM WISHLIST")
                    .setMessage("are ypu sure want to delete movie from WISHLIST?")
                    .setPositiveButton("yes") { dialog, i ->
                        lifecycleScope.async {
                            movieVievModel.deleteSingleMovieFromWishlist(itemSwiped.id)
                            withContext(Dispatchers.Main){
                                Snackbar.make(this@FavoriteMoviesFragment.view!!, "movie DELETED", Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    }
                    .setNegativeButton("no") { dialog, i ->
                        lifecycleScope.launch {
                            movieVievModel.fakeDeleteOfItemMovie(itemSwiped)
                        }
                        movieWishListAdapter.notifyDataSetChanged()
                    }
                    .setCancelable(true)
                    .create()
                    .show()
            }
        }
        ItemTouchHelper(callBack).apply {
            attachToRecyclerView(binding.recVievPlaceHolderOnWishlist)
        }
    }

    private fun initProgBar() {
        movieVievModel.loading.observe(viewLifecycleOwner) {
            binding.apply {
                if (it) {
                    recVievPlaceHolderOnWishlist.visibility = View.INVISIBLE
                    textViewNoFavMovies.visibility = View.INVISIBLE
                    imgNoFavoritesMovies.visibility = View.INVISIBLE
                    progBarFav.visibility = View.VISIBLE
                } else {
                    recVievPlaceHolderOnWishlist.visibility = View.VISIBLE
                    textViewNoFavMovies.visibility = View.VISIBLE
                    imgNoFavoritesMovies.visibility = View.VISIBLE
                    progBarFav.visibility = View.INVISIBLE
                }
            }
        }
    }
/*
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteMoviesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
 */
}