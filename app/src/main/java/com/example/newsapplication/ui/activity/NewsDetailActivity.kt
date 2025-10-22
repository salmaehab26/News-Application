package com.example.newsapplication.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.apiintegrationapp.response.Article
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ActivityNewsDetailBinding
import com.example.newsapplication.ui.viewModel.NewsDetailViewModel

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding
    private lateinit var viewModel: NewsDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[NewsDetailViewModel::class.java]

        val article = intent.getParcelableExtra<Article>("article")
        article?.let {
            viewModel.setArticle(it)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.article.observe(this) { article ->
            binding.title.text = article.title?:""
            binding.description.text = article.description?:""
            binding.date.text = article.publishedAt?:""

            Glide.with(this)
                .load(article.urlToImage)
                .placeholder(R.drawable.news_en_1920x1080)
                .error(R.drawable.ic_launcher_background)
                .into(binding.image)

        }
    }
}
