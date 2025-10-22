package com.example.newsapplication.domain.useCase

import com.example.apiintegrationapp.response.Article
import com.example.newsapplication.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow

class NewsUseCase (private val repository: INewsRepository) {

    operator fun invoke(): Flow<List<Article>> {
        return repository.getRemoteNews()
    }}