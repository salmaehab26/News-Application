package com.example.newsapplication.domain.useCase

import com.example.apiintegrationapp.response.Article
import com.example.newsapplication.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCase @Inject constructor(
    private val repository: INewsRepository
) {
    suspend operator fun invoke(page: Int, pageSize: Int): List<Article> {
        return repository.getRemoteNews(page, pageSize)
    }
}
