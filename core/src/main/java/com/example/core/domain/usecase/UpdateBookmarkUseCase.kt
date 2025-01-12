
package com.example.core.domain.usecase

import com.example.core.data.NewsRepositoryImpl
import com.example.core.domain.model.News

class UpdateBookmarkUseCase(private val newsRepository: NewsRepositoryImpl) {
    suspend fun execute(news: News, isBookmarked: Boolean) {
        news.isBookmarked = isBookmarked
        newsRepository.setBookmarkedNews(news, isBookmarked)
    }
}
