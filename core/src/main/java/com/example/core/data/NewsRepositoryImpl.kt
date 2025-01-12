// Data Layer
package com.example.core.data


import com.example.core.domain.Result
import com.example.core.domain.model.News
import com.example.core.domain.repository.INewsRepository
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(private val newsDao: NewsDao) : INewsRepository {
    override fun getHeadlineNews(): Flow<Result<List<News>>> {
        TODO("Not yet implemented")
    }

    override fun getBookmarkedNews(): Flow<List<News>> {
        TODO("Not yet implemented")
    }

    override suspend fun setBookmarkedNews(news: News, state: Boolean) {
        val newsEntity = DataMapper.mapDomainToEntity(news)
        newsDao.updateNews(newsEntity)
    }
}
