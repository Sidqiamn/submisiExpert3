package com.example.core.domain.usecase


import com.example.core.domain.Result
import com.example.core.domain.model.News
import com.example.core.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow

class NewsUseCaseImpl(
    private val newsRepository: INewsRepository
) : NewsUseCase {
    override fun getHeadlineNews(): Flow<Result<List<News>>> {
        return newsRepository.getHeadlineNews()
    }

    override fun getBookmarkedNews(): Flow<List<News>> {
        return newsRepository.getBookmarkedNews()
    }

    override suspend fun setBookmarkedNews(news: News, state: Boolean) {
        newsRepository.setBookmarkedNews(news, state)
    }
}
