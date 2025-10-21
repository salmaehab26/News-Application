package com.example.newsapplication.domain

class NewsUseCase (private val repository: INewsRepository) {
    operator fun invoke(country: String) = repository.getNews(country)
}