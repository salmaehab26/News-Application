package com.example.newsapplication.domain.useCase

import com.example.newsapplication.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalNewsCountUseCase @Inject constructor(
    private val repository: INewsRepository
) {
    operator fun invoke(): Flow<Int> {
        return repository.getLocalNewsCount()
    }
}