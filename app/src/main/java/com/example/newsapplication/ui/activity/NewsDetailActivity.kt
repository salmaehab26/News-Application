package com.example.newsapplication.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.newsapplication.R
import com.example.newsapplication.data.dataSource.local.NewsEntity
import com.example.newsapplication.databinding.ActivityNewsDetailBinding
import com.example.newsapplication.ui.viewModel.NewsDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding
    private val viewModel: NewsDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val imageUrl = intent.getStringExtra("imageUrl")

        val newsEntity = NewsEntity(
            id = 0,
            title = title ?: "",
            description = description,
            urlToImage = imageUrl,
            author = "",
            publishedAt = ""
        )

        viewModel.setArticle(newsEntity)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}

