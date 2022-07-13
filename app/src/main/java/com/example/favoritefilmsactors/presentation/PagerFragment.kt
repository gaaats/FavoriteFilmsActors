package com.example.favoritefilmsactors.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.favoritefilmsactors.databinding.FragmentMoviesImagePagerBinding
import com.example.favoritefilmsactors.presentation.recviev.MovieListAdapter
import com.example.favoritefilmsactors.presentation.recviev.PagerAdapter
import com.example.favoritefilmsactors.presentation.vievmodels.MovieVievModel
import com.example.favoritefilmsactors.presentation.vievmodels.MovieVievModelFactory
import com.example.favoritefilmsactors.presentation.vievmodels.PagerMovieVievModel
import com.example.favoritefilmsactors.utils.constance.Constance
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PagerFragment : Fragment() {

    @Inject
    lateinit var vievModelfactory: MovieVievModelFactory
    @Inject
    lateinit var pagerAdapter: PagerAdapter

    private val movieVievModel by lazy {
        ViewModelProvider(this, vievModelfactory)[PagerMovieVievModel::class.java]
    }

    val args: PagerFragmentArgs by navArgs()
    private var _binding: FragmentMoviesImagePagerBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentMoviesImagePagerBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesImagePagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val currentMovieId = args.movieId
        Log.d(Constance.TAG, "current id is: $currentMovieId")
        super.onViewCreated(view, savedInstanceState)

        initLoadOfImages(currentMovieId)
        iniAdapterSubmitList()
    }

    private fun initLoadOfImages(currentMovieId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            movieVievModel.loadImagesList(currentMovieId)
        }
    }

    private fun iniAdapterSubmitList() {
        binding.viewPager.adapter = pagerAdapter
        movieVievModel.listImages.observe(viewLifecycleOwner) {
            pagerAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}