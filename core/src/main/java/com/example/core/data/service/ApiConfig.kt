package com.example.core.data.service


import com.example.core.BuildConfig
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {

            val loggingInterceptor = HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
            )

            val hostname = "event-api.dicoding.dev"


            val certificatePinner = CertificatePinner.Builder()
                .add(hostname, "sha256/IP3deCdJNWm0Ae27av8Odv7gpd7Z1jL6dKVGnJDOpDM=")
                .add(hostname, "sha256/K7rZOrXHknnsEhUH8nLL4MZkejquUuIvOIr6tCa0rbo=")
                .build()


            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .certificatePinner(certificatePinner)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }

        private const val BASE_URL = BuildConfig.BASE_URL
    }
}
