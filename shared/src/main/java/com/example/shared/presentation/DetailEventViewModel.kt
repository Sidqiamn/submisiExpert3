package com.example.shared.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.News
import com.example.core.domain.usecase.NewsUseCase
import kotlinx.coroutines.launch

class DetailEventViewModel(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    fun updateBookmark(news: News, isBookmarked: Boolean) {
        viewModelScope.launch {
            try {
                newsUseCase.setBookmarkedNews(news, isBookmarked)
            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}
