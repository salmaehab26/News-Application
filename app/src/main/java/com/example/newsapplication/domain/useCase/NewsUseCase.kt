package com.example.newsapplication.domain.useCase

import androidx.paging.PagingData
import com.example.newsapplication.data.dataSource.local.NewsEntity
import com.example.newsapplication.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCase @Inject constructor(
    private val repository: INewsRepository
) {
      operator fun invoke():  Flow<PagingData<NewsEntity>> {
        return repository.getPagedNews()
    }
}
