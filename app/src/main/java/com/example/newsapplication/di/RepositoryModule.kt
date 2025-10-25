package com.example.newsapplication.di

import com.example.newsapplication.data.repository.NewsRepositoryImpl
import com.example.newsapplication.domain.repository.INewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        repositoryImpl: NewsRepositoryImpl
    ): INewsRepository
}