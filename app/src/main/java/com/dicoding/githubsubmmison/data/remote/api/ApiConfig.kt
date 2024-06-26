package com.dicoding.githubsubmmison.data.remote.api

import com.dicoding.githubsubmmison.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level =
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            val interceptor = Interceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .addHeader("Authorization", BuildConfig.KEY)
                        .build()
                )
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(interceptor)
                .build()

            val getRetrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return getRetrofit.create(ApiService::class.java)
        }
    }
}