package com.example.newsapplication.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ActivityMainBinding
import com.example.newsapplication.ui.adapter.NewsAdapter
import com.example.newsapplication.ui.adapter.NewsLoadStateAdapter
import com.example.newsapplication.ui.viewModel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm: NewsViewModel by viewModels()
    private val adapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = vm
        binding.lifecycleOwner = this

        setupRecyclerView()
        observePagingData()
        handleLoadState()
    }


     fun setupRecyclerView() {
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = adapter.withLoadStateHeaderAndFooter(
            header = NewsLoadStateAdapter { adapter.retry() },
            footer = NewsLoadStateAdapter { adapter.retry() }
        )
    }

     fun observePagingData() {
        lifecycleScope.launch {
            vm.pagedNews.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

     fun handleLoadState() {
        lifecycleScope.launch {
            combine(
                adapter.loadStateFlow,
                vm.isDatabaseEmpty
            ) { loadState, isEmpty ->
                val isLoading = loadState.refresh is LoadState.Loading
                val isError = loadState.refresh is LoadState.Error

                binding.progressBar.isVisible = isLoading
                binding.rvNews.isVisible = !isLoading && !isEmpty && !isError
                binding.noConnectionText.isVisible = !isLoading && (isEmpty || isError)
            }.collect()
        }
    }
}
