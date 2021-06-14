package com.bartuciotti.torontowanted.pages.news.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<News>)

    @Query("DELETE FROM news")
    suspend fun deleteNews()

    @Query("SELECT * FROM news ORDER BY news.id DESC")
    fun getNews(): LiveData<List<News>>

    @Query("SELECT count(*) FROM news")
    suspend fun getCountNewsTable(): Int
}