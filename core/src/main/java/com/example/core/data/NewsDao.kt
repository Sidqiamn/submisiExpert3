package com.example.core.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM news ORDER BY publishedAt DESC")
    fun getNews(): Flow<List<NewsEntity>>


    @Query("SELECT * FROM news WHERE bookmarked = 1")
    fun getBookmarkedNews(): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(news: List<NewsEntity>)


    @Update
    suspend fun updateNews(news: NewsEntity)

    @Query("DELETE FROM news WHERE bookmarked = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM news WHERE title = :title AND bookmarked = 1)")
    suspend fun isNewsBookmarked(title: String): Boolean
}
