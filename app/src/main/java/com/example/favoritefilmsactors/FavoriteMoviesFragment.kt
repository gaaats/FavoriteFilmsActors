package com.example.favoritefilmsactors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.favoritefilmsactors.databinding.FragmentFavoriteMoviesBinding
import com.example.favoritefilmsactors.databinding.FragmentMoviesListBinding
import com.example.favoritefilmsactors.presentation.recviev.MovieListAdapter
import com.example.favoritefilmsactors.presentation.recviev.MovieWishListAdapter
import com.example.favoritefilmsactors.presentation.vievmodels.MovieVievModel
import com.example.favoritefilmsactors.presentation.vievmodels.MovieVievModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initAdapterAndLoadMoviesWishlist() {
        lifecycleScope.launch {
            movieVievModel.loadFavoriteMovies()
        }
        binding.recVievPlaceHolderOnWishlist.adapter = movieWishListAdapter
        movieVievModel.listFavoriteMovies.observe(viewLifecycleOwner) {
            movieWishListAdapter.submitList(it)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
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