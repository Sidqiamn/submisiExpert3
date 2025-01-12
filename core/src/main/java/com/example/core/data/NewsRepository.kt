package com.example.core.data

import android.util.Log
import com.example.core.data.service.ApiService
import com.example.core.domain.Result
import com.example.core.domain.model.News
import com.example.core.domain.repository.INewsRepository
import com.example.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class NewsRepository private constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
    private val appExecutors: AppExecutors
) : INewsRepository {

    override fun getHeadlineNews(): Flow<Result<List<News>>> = flow {
        emit(Result.Loading)

        try {

            val response = apiService.getActiveEvents()
            val articles = response.listEvents

            Log.d("NewsRepository", "Data API berhasil diambil: ${articles.size} artikel")

            val newsEntities = DataMapper.mapResponsesToEntities(articles)

            newsDao.deleteAll()
            newsDao.insertNews(newsEntities)

            val localData = newsDao.getNews()
            emitAll(
                localData.map { entities ->
                    val domainData = DataMapper.mapEntitiesToDomain(entities)
                    Result.Success(domainData)
                }
            )
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("NewsRepository", "Gagal mengambil data dari API: ${e.message}")
        }
    }.catch { e ->
        emit(Result.Error("Error: ${e.message}"))
        Log.e("NewsRepository", "Error Flow: ${e.message}")
    }.flowOn(Dispatchers.IO)

    override fun getBookmarkedNews(): Flow<List<News>> =
        newsDao.getBookmarkedNews()
            .map { entities ->
                DataMapper.mapEntitiesToDomain(entities)
            }
            .catch { e ->
                Log.e("NewsRepository", "Error Bookmarked Flow: ${e.message}")
            }
            .flowOn(Dispatchers.IO)

    override suspend fun setBookmarkedNews(news: News, state: Boolean) {
        val newsEntity = DataMapper.mapDomainToEntity(news).apply {
            isBookmarked = state
        }
        newsDao.updateNews(newsEntity)
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao,
            appExecutors: AppExecutors
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newsDao, appExecutors)
            }.also { instance = it }
    }
}
