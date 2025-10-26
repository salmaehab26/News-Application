package com.example.newsapplication.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.newsapplication.data.dataSource.local.NewsEntity


class DiffCallback : DiffUtil.ItemCallback<NewsEntity>() {
    override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity) =
        oldItem == newItem
}
