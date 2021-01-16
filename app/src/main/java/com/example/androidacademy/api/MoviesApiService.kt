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

private const val API_KEY = "3847095cad8449ec1b9ca6240fa9102c"
private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY_HEADER = "x-api-key"

interface MoviesApiService {
    @GET("images/search?size=small&order=RANDOM&limit=5&format=json")
    suspend fun getMovies(): List<Movie>
}