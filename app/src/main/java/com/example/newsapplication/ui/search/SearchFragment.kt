package com.example.newsapplication.ui.search

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplication.databinding.FragmentSearchBinding
import com.example.newsapplication.ui.adapters.NewsAdapter
import com.example.newsapplication.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<SearchViewModel>()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        var job: Job? = null
        mBinding.edSearch.addTextChangedListener { text: Editable? ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                text?.let {
                    if(it.toString().isNotEmpty()){
                        viewModel.getSearchNews(query = it.toString())
                    }
                }
            }

        }
        viewModel.searchNewsLiveData.observe(viewLifecycleOwner){response ->
            when(response){
                is Resource.Error -> {
                    mBinding.pagSearchProgressBar.visibility = View.INVISIBLE
                    response.data?.let{
                        Log.e("MainFragment", "MainFragment error ${it}")
                    }
                }
                is Resource.Loading -> {
                    mBinding.pagSearchProgressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    mBinding.pagSearchProgressBar.visibility = View.INVISIBLE
                    response.data?.let{
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        mBinding.searchNewsAdapter.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}