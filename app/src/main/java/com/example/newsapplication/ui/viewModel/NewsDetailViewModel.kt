package com.example.newsapplication.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapplication.data.dataSource.local.NewsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NewsDetailViewModel @Inject constructor() : ViewModel() {

     val _article = MutableLiveData<NewsEntity>()
    val article: LiveData<NewsEntity> = _article

    fun setArticle(entity: NewsEntity) {
        _article.value = entity
    }
}