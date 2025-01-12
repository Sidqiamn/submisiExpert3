package com.example.core.data.service

import com.example.core.data.EventResponse
import retrofit2.http.GET

interface ApiService {
    @GET("events?active=0")
  suspend  fun getActiveEvents(): EventResponse
}