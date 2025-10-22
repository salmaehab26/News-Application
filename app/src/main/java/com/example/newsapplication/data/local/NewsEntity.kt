package com.example.newsapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")

data class NewsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String?,
    val urlToImage: String?,
    val description: String?


)

