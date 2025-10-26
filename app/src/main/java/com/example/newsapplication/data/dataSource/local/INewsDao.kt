package com.example.newsapplication.data.dataSource.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface INewsDao {
    @Query("SELECT * FROM news ")
    fun getAllNewsPaging(): PagingSource<Int, NewsEntity>

    @Query("SELECT COUNT(*) FROM news")
    fun getNewsCount(): Flow<Int>

    @Query("SELECT * FROM news")
    suspend fun getAllNewsOnce(): List<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsEntity>)

    @Query("DELETE FROM news")
    suspend fun clearAll()
}
