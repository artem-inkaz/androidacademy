package com.example.androidacademy.api

import com.example.androidacademy.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

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

