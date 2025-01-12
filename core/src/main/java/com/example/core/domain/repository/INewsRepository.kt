package com.example.core.domain.repository

import com.example.core.domain.Result
import com.example.core.domain.model.News
import kotlinx.coroutines.flow.Flow

interface INewsRepository {

    fun getHeadlineNews(): Flow<Result<List<News>>>

    fun getBookmarkedNews(): Flow<List<News>>

    suspend fun setBookmarkedNews(news: News, state: Boolean)
}
