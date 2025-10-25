package com.example.newsapplication.data.repository

import android.content.Context
import com.example.apiintegrationapp.response.Article
import com.example.newsapplication.data.local.INewsDao
import com.example.newsapplication.data.local.NewsEntity
import com.example.newsapplication.data.remote.IApiManager
import com.example.newsapplication.domain.repository.INewsRepository
import com.example.newsapplication.utils.NetworkUtils.isNetworkAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val dao: INewsDao,
    @ApplicationContext private val context: Context,
    private val api: IApiManager
) : INewsRepository {

    override suspend fun getRemoteNews(page: Int, pageSize: Int): List<Article> {
        return try {
            if (isNetworkAvailable(context)) {
                val response = api.getNews("us", "72f42bc2588c4688a72cf0acc7ee280f", page, pageSize)
                val articles = response.articles ?: emptyList()

                dao.insertAll(articles.map {
                    NewsEntity(
                        title = it.title ?: "",
                        author = it.author ?: "",
                        urlToImage = it.urlToImage,
                        description = it.description
                    )
                })
                articles
            } else {
                dao.getAllNewsOnce().map {
                    Article(
                        author = it.author,
                        title = it.title,
                        description = it.description,
                        urlToImage = it.urlToImage,
                        url = "",
                        publishedAt = "",
                        content = "",
                        source = com.example.apiintegrationapp.response.Source("", "")
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override fun getLocalNews(): Flow<List<Article>> {
        return dao.getAllNews().map { list ->
            list.map {
                Article(
                    author = it.author,
                    title = it.title,
                    description = it.description,
                    urlToImage = it.urlToImage,
                    url = "",
                    publishedAt = "",
                    content = "",
                    source = com.example.apiintegrationapp.response.Source("", "")
                )
            }
        }
    }
}
