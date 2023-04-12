package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.ui.adapters.NewsAdapter
import com.example.newsapp.ui.utils.Resource

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news){

    lateinit var viewModel : NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    val rvBreakingNews = view?.findViewById<RecyclerView>(R.id.rvBreakingNews)
    val paginationProgressBar = view?.findViewById<RecyclerView>(R.id.paginationProgressBar)
    val TAG = "BreakingNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        setUpRecyclerView()

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse!!.articles)
                    }
                }

                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let {message ->
                        Log.e(TAG, "An error occured $message")
                    }
                }

                is Resource.Loading->{
                    showingProgressBar()
                }

            }
        })
    }

    private fun hideProgressBar() {
        paginationProgressBar!!.isVisible = false
    }
    private fun showingProgressBar(){
        paginationProgressBar!!.isVisible = true
    }

    private fun setUpRecyclerView(){
        newsAdapter = NewsAdapter()
        rvBreakingNews?.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}