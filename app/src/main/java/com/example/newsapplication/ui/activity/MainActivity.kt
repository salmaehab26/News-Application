package com.example.newsapplication.ui.activity
import com.example.newsapplication.data.local.NewsDatabase

import NewsViewModel
import com.example.newsapplication.ui.viewModel.NewsViewModelFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.newsapplication.data.repository.NewsRepositoryImpl
import com.example.newsapplication.databinding.ActivityMainBinding
import com.example.newsapplication.domain.useCase.NewsUseCase
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

        val database = Room.databaseBuilder(
            applicationContext,
            NewsDatabase::class.java,
            "news_db"
        ).build()

        val dao = database.newsDao()
        val repository = NewsRepositoryImpl(dao, applicationContext)
        val useCase = NewsUseCase(repository)
        val factory = NewsViewModelFactory(useCase)

        viewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]

        val adapter = NewsAdapter(this)
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.newsFlow.collectLatest { articles ->
                    Log.d("MainActivity", "Got ${articles.size} articles")
                    adapter.updateNews(articles)
                }
            }
        }
    }
}