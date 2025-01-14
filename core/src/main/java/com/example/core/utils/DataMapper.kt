package com.example.core.utils

import com.example.core.data.NewsEntity
import com.example.core.data.ListEventsItem
import com.example.core.domain.model.News


object DataMapper {
    fun mapResponsesToEntities(input: List<ListEventsItem>): List<NewsEntity> {
        val tourismList = ArrayList<NewsEntity>()
        input.map {
            val tourism = NewsEntity(
                title = it.name,
                desckripsi = it.description,
                summary = it.summary,
                penyelenggaraAcara = it.ownerName,
                waktu = it.beginTime,
                quota = it.quota,
                link = it.link,
                urlToImage = it.imageLogo,
                publishedAt = it.beginTime,
                isBookmarked = false
            )
            tourismList.add(tourism)
        }
        return tourismList
    }
    fun mapEntitiesToDomain(input: List<NewsEntity>): List<News> =
        input.map {
            News(
                summary = it.summary,
                description = it.desckripsi,
                link = it.link,
                imageLogo = it.urlToImage.toString(),
                 ownerName  = it.penyelenggaraAcara,
                 quota = it.quota,
                name = it.title,
                id = it.title,
                beginTime =it.waktu,
                category = it.title,
                cityName = it.title,
                endTime = it.waktu,
                mediaCover = it.url.toString(),
                isBookmarked = it.isBookmarked
            )
        }

    fun mapDomainToEntity(input: News) = NewsEntity(
        title = input.name,
        summary = input.summary,
        penyelenggaraAcara = input.name,
        waktu = input.beginTime,
        quota = input.quota,
        link = input.link,
        desckripsi = input.description,
        publishedAt = input.beginTime,
        urlToImage = input.imageLogo,
        url = input.link,
        isBookmarked = input.isBookmarked
    )

}