package com.example.newsapplication.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplication.databinding.ActivityMainBinding
import com.example.newsapplication.ui.adapter.NewsAdapter
import com.example.newsapplication.ui.viewModel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm: NewsViewModel by viewModels()
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NewsAdapter(this)
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = adapter

        setupClicks()
        vm.loadApiPage(page = 1, apiPageSize = 100)

        lifecycleScope.launch {
            vm.pagedNews.collectLatest { pageArticles ->
                adapter.updateNews(pageArticles)
                binding.rvNews.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            vm.currentPage.collectLatest { cur ->
                val total = vm.totalPages.value
                binding.pageNumber.text = "$cur / $total"
            }
        }
    }

    private fun setupClicks() {
        binding.btnNext.setOnClickListener { vm.nextPage() }
        binding.btnPrev.setOnClickListener { vm.prevPage() }
    }
}
