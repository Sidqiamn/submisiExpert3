package com.example.shared.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.Result
import com.example.core.domain.model.News
import com.example.core.domain.usecase.NewsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsViewModel(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    private val _headlineNews = MutableStateFlow<Result<List<News>>>(Result.Loading)
    val headlineNews: StateFlow<Result<List<News>>> = _headlineNews

    private val _bookmarkedNews = MutableStateFlow<List<News>>(emptyList())
    val bookmarkedNews: StateFlow<List<News>> = _bookmarkedNews

    init {
        fetchHeadlineNews()
        fetchBookmarkedNews()
    }

    fun fetchHeadlineNews() {
        viewModelScope.launch {
            newsUseCase.getHeadlineNews()
                .catch { e -> _headlineNews.value = Result.Error(e.toString()) }
                .collect { result -> _headlineNews.value = result }
        }
    }

    fun fetchBookmarkedNews() {
        viewModelScope.launch {
            newsUseCase.getBookmarkedNews()
                .catch { _ -> _bookmarkedNews.value = emptyList() }
                .collect { newsList -> _bookmarkedNews.value = newsList }
        }
    }

    fun saveNews(news: News) {
        viewModelScope.launch {
            newsUseCase.setBookmarkedNews(news, true)
        }
    }

    fun deleteNews(news: News) {
        viewModelScope.launch {
            newsUseCase.setBookmarkedNews(news, false)
        }
    }
}
