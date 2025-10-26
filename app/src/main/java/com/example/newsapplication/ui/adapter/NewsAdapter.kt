package com.example.newsapplication.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.data.dataSource.local.NewsEntity
import com.example.newsapplication.databinding.NewsItemBinding
import com.example.newsapplication.ui.activity.NewsDetailActivity

class NewsAdapter :
    PagingDataAdapter<NewsEntity, NewsAdapter.NewsViewHolder>(DiffCallback()) {

    inner class NewsViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: NewsEntity) {
            binding.article = entity
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, NewsDetailActivity::class.java)
                intent.putExtra("title", entity.title)
                intent.putExtra("description", entity.description)
                intent.putExtra("imageUrl", entity.urlToImage)
                context.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val entity = getItem(position) ?: return
        holder.bind(entity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

}
