package com.example.newsapplication.ui.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.apiintegrationapp.response.Article
import com.example.newsapplication.R
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

        val article = intent.getParcelableExtra<Article>("article")
        article?.let { viewModel.setArticle(it) }
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        intent.getParcelableExtra<Article>("article")?.let {
            viewModel.setArticle(it)
        }
    }


}
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.news_en_1920x1080)
        .error(R.drawable.ic_launcher_background)
        .into(view)
}