package com.example.newsapplication.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiintegrationapp.response.Article
import com.example.newsapplication.domain.useCase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    // raw articles gathered (can be partial or full)
    private val _allNews = MutableStateFlow<List<Article>>(emptyList())
    val allNews: StateFlow<List<Article>> = _allNews

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage

    // UI page size (how many items we show per UI page)
    private val pageSize = 6

    // Combine to compute the UI page content
    val pagedNews: StateFlow<List<Article>> = combine(_allNews, _currentPage) { news, page ->
        val from = (page - 1) * pageSize
        val to = min(from + pageSize, news.size)
        if (from < news.size) news.subList(from, to) else emptyList()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val totalPages: StateFlow<Int> = _allNews.map { list ->
        if (list.isEmpty()) 1 else (list.size + pageSize - 1) / pageSize
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 1)

    // --- simple fetch: fetch single API page (caller chooses pageSize)
    fun loadApiPage(page: Int = 1, apiPageSize: Int = 100) {
        viewModelScope.launch {
            val articles = newsUseCase(page, apiPageSize)
            _allNews.value = articles
            _currentPage.value = 1
        }
    }

    // --- fetch all pages from server (careful: can be heavy)
    fun fetchAllFromServer(apiPageSize: Int = 100) {
        viewModelScope.launch {
            val gathered = mutableListOf<Article>()
            var page = 1
            while (true) {
                val response = newsUseCase(page, apiPageSize)
                if (response.isEmpty()) break
                gathered.addAll(response)
                // stop if returned less than requested (last page)
                if (response.size < apiPageSize) break
                page++
            }
            _allNews.value = gathered
            _currentPage.value = 1
        }
    }

    fun nextPage() {
        if (_currentPage.value < totalPages.value) _currentPage.value += 1
    }

    fun prevPage() {
        if (_currentPage.value > 1) _currentPage.value -= 1
    }
}
