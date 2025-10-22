package com.example.newsapplication.data.repository

import android.content.Context
import android.util.Log
import com.example.apiintegrationapp.response.Article
import com.example.apiintegrationapp.response.Source
import com.example.newsapplication.data.local.INewsDao
import com.example.newsapplication.data.local.NewsEntity
import com.example.newsapplication.data.remote.RetrofitInstance
import com.example.newsapplication.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow
import com.example.newsapplication.utils.NetworkUtils.isNetworkAvailable
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val dao: INewsDao, private val context: Context

) : INewsRepository {

    override fun getRemoteNews(): Flow<List<Article>> = flow {
        if (isNetworkAvailable(context)) {
            Log.d("NewsRepo", "Network available ✅")

            try {
            val response = RetrofitInstance.api.getNews("us", "72f42bc2588c4688a72cf0acc7ee280f")
            Log.d("NewsRepo", "Received ${response.articles.size} articles from API")

            dao.clearAll()
            dao.insertAll(response.articles.map {
                NewsEntity(
                    title = it.title ?: "",
                    author = it.author ?: "",
                    urlToImage = it.urlToImage ?: "",
                    description = it.description ?: ""
                )
            })
            emit(response.articles)
        } catch (e: Exception) {
            e.printStackTrace()
            emitAll(getLocalNews())
        }
        } else {
            Log.d("NewsRepo", "Network not available ❌")

            emitAll(getLocalNews())
        }
    }

    override fun getLocalNews(): Flow<List<Article>> =
        dao.getAllNews().map { entities ->
            entities.map {
                Article(
                    author = it.author ?: "",
                    content = "",
                    description = it.description ?: "",
                    publishedAt = "",
                    source = Source("", ""),
                    title = it.title ?: "",
                    url = "",
                    urlToImage = it.urlToImage ?: ""
                )
            }
        }
}