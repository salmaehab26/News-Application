package com.example.newsapplication.ui.activity

import NewsViewModel
import com.example.newsapplication.ui.viewModel.NewsViewModelFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplication.data.Repository.NewsRepositoryImpl
import com.example.newsapplication.databinding.ActivityMainBinding
import com.example.newsapplication.domain.NewsUseCase
import com.example.newsapplication.ui.adapter.NewsAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = NewsRepositoryImpl()
        val useCase = NewsUseCase(repository)
        val factory = NewsViewModelFactory(useCase)

        viewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]

        val adapter = NewsAdapter()
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.newsFlow.collectLatest { articles ->
                    adapter.updateNews(articles)
                }
            }
        }
    }}