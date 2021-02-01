package com.example.androidacademy.api

import com.example.androidacademy.BuildConfig
import com.example.androidacademy.data.Movie
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val API_KEY_HEADER = "x-api-key"


class MoviesApiHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val request = originalRequest.newBuilder()
                .url(originalHttpUrl)
                .addHeader(API_KEY_HEADER, BuildConfig.API_KEY)
                .build()

        return chain.proceed(request)
    }
}

