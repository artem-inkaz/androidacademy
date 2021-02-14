package com.example.androidacademy.movieslist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidacademy.App
import com.example.androidacademy.api.RetrofitModule
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.create


class MoviesViewModelFactory : ViewModelProvider.Factory {
    @ExperimentalSerializationApi
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(
            apiService = RetrofitModule.retrofit.create(),
            repository = App.repository()
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}