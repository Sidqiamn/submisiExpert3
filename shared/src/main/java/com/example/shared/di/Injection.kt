package com.example.shared.di

import com.example.core.data.AppExecutors
import com.example.core.data.NewsRepository
import com.example.core.data.NewsDatabase
import com.example.core.data.service.ApiConfig
import com.example.core.domain.repository.INewsRepository
import com.example.core.domain.usecase.NewsInteractor
import com.example.core.domain.usecase.NewsUseCase
import com.example.shared.presentation.DetailEventViewModel
import com.example.shared.presentation.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { ApiConfig.getApiService() }
    single { AppExecutors() }

    single { NewsDatabase.getInstance(get()) }
    single { get<NewsDatabase>().newsDao() }

    single { NewsRepository.getInstance(get(), get(), get()) as INewsRepository }


    single { NewsInteractor(get()) as NewsUseCase }


    viewModel { DetailEventViewModel(get()) }
    viewModel { NewsViewModel(get()) }
}
