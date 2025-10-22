package com.example.newsapplication.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apiintegrationapp.response.Article

class NewsDetailViewModel : ViewModel() {

    private val _article = MutableLiveData<Article>()
    val article: LiveData<Article> get() = _article

    fun setArticle(article: Article) {
        _article.value = article
    }
}