package com.example.newsapplication.di

import com.example.newsapplication.data.dataSource.local.INewsDao
import android.content.Context
import androidx.room.Room
import com.example.newsapplication.data.dataSource.local.NewsDatabase
import com.example.newsapplication.data.dataSource.remote.IApiManager
import com.example.newsapplication.data.dataSource.remote.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase =
        Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideDao(db: NewsDatabase): INewsDao = db.newsDao()


    @Provides
    @Singleton
    fun provideApiManager(): IApiManager = RetrofitInstance.api
}
