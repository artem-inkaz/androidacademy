package com.example.androidacademy.moviesdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidacademy.api.RetrofitModule
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.create

class MoviesDetailViewModelFactory : ViewModelProvider.Factory {

    @ExperimentalSerializationApi
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesDetailsViewModel::class.java -> MoviesDetailsViewModel(apiService = RetrofitModule.retrofit.create())
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T

}
