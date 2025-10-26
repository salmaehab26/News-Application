package com.example.newsapplication.ui.viewModel

import com.example.newsapplication.domain.useCase.GetLocalNewsCountUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapplication.domain.useCase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
     val getNewsUseCase: NewsUseCase,
     val getLocalNewsCountUseCase: GetLocalNewsCountUseCase
) : ViewModel() {

    val isDatabaseEmpty = getLocalNewsCountUseCase()
        .map { it == 0 }
        .stateIn(viewModelScope, SharingStarted.Lazily, true)

    val pagedNews = getNewsUseCase()
        .cachedIn(viewModelScope)
}
