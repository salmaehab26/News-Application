package com.example.newsapplication.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apiintegrationapp.response.Article
import com.example.newsapplication.databinding.NewsItemBinding
import com.example.newsapplication.ui.activity.NewsDetailActivity

class NewsAdapter(
    private val context: Context
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val newsList = mutableListOf<Article>()

    inner class ViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.tvTitle.text = article.title

            Glide.with(binding.root.context)
                .load(article.urlToImage)
                .placeholder(com.example.newsapplication.R.drawable.news_en_1920x1080)
                .error(com.example.newsapplication.R.drawable.ic_launcher_background)
                .into(binding.newsImage)

            binding.root.setOnClickListener {
                val intent = Intent(context, NewsDetailActivity::class.java)
                intent.putExtra("article", article)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(newsList[position])

    override fun getItemCount(): Int = newsList.size

    fun updateNews(newList: List<Article>) {
        val diffResult = DiffUtil.calculateDiff(NewsDiffUtil(newsList, newList))
        newsList.clear()
        newsList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}
