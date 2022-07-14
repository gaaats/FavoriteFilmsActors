package com.example.favoritefilmsactors.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.favoritefilmsactors.databinding.FragmentNevsInfoBinding
import com.example.favoritefilmsactors.domain.entity.ArticleUIModel
import com.example.favoritefilmsactors.presentation.recviev.nevs.NevsListAdapter
import com.example.favoritefilmsactors.presentation.vievmodels.NevsVievModelFactory
import com.example.favoritefilmsactors.presentation.vievmodels.NewsViewModel
import com.example.favoritefilmsactors.utils.constance.Constance
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NevsInfoFragment : Fragment() {

    @Inject
    lateinit var vievModelfactory: NevsVievModelFactory

    @Inject
    lateinit var nevsAdapter: NevsListAdapter

    val nevsVievModel by lazy {
        ViewModelProvider(this, vievModelfactory)[NewsViewModel::class.java]
    }

    val newsArgs: NevsInfoFragmentArgs by navArgs()

    private var _binding: FragmentNevsInfoBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentNevsInfoBinding is null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNevsInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initScreenLoading()
        val nevsItemForLoading = newsArgs.currentNevsItem
            ?: throw RuntimeException("null in nevsItemForLoading NevsInfoFragment")

        tryMakeLoad(nevsItemForLoading)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    private fun tryMakeLoad(nevsItemForLoading: ArticleUIModel) {
        lifecycleScope.launch {
            if (nevsItemForLoading.url != "default url") {
                binding.webViewInfo.apply {
                    try {
                        webViewClient = WebViewClient()
                        loadUrl(nevsItemForLoading.url)
                        delay(1500)
                        binding.webViewInfo.alpha = 1F
                    } catch (e: Exception) {
                        binding.imgError.visibility = View.VISIBLE
                        Log.d(Constance.TAG, "Error in NevsInfoFragment - ${e.message.toString()}")
                    }
                }
                binding.progBarOnVebViewInfo.visibility = View.INVISIBLE
            }
        }
    }

    private fun initScreenLoading() {
        binding.imgError.visibility = View.GONE
        binding.webViewInfo.alpha = 0.1F
        binding.progBarOnVebViewInfo.visibility = View.VISIBLE
    }




}