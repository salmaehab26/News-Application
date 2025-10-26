package com.example.newsapplication.data.dataSource.local
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsEntity::class], version = 2, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): INewsDao
}