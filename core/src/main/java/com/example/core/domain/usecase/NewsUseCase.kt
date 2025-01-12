package com.example.core.domain.usecase

import com.example.core.domain.Result
import com.example.core.domain.model.News
import kotlinx.coroutines.flow.Flow


interface NewsUseCase {
    fun getHeadlineNews(): Flow<Result<List<News>>>
    fun getBookmarkedNews(): Flow<List<News>>
    suspend fun setBookmarkedNews(news: News, state: Boolean)
}