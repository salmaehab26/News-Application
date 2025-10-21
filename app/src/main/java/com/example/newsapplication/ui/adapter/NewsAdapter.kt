package com.example.newsapplication.ui.adapter

import NewsDiffUtil
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apiintegrationapp.response.Article
import com.example.newsapplication.databinding.NewsItemBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val newsList = mutableListOf<Article>()

    inner class ViewHolder(val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Article) {
            binding.tvTitle.text = item.title
            Glide.with(binding.root.context)
                .load(item.urlToImage)
                .into(binding.newsImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size

    fun updateNews(newList: List<Article>) {
        Log.d("NewsAdapter", "Received ${newList.size} articles")
        val diffCallback = NewsDiffUtil(newsList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        newsList.clear()
        newsList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}
