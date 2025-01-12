package com.example.core.domain.usecase

import com.example.core.domain.model.News
import com.example.core.domain.repository.INewsRepository


class NewsInteractor(private val newsRepository: INewsRepository): NewsUseCase {

    override fun getHeadlineNews() = newsRepository.getHeadlineNews()

    override fun getBookmarkedNews() = newsRepository.getBookmarkedNews()

    override suspend fun setBookmarkedNews(news: News, state: Boolean) = newsRepository.setBookmarkedNews(news, state)
}