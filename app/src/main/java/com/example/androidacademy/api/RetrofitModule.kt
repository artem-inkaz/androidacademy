package com.example.androidacademy.api

import com.example.androidacademy.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

private const val API_KEY = "3847095cad8449ec1b9ca6240fa9102c"
private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY_HEADER = "x-api-key"

object RetrofitModule {
    private val client = OkHttpClient().newBuilder()
        .addInterceptor(MoviesApiHeaderInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @ExperimentalSerializationApi
    @Suppress("EXPERIMENTAL_API_USAGE")
    val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
}