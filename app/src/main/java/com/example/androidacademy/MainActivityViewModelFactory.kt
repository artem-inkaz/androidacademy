package com.example.androidacademy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidacademy.repositories.RepositoryHolder
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.create

class MainActivityViewModelFactory : ViewModelProvider.Factory {

    @ExperimentalSerializationApi
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MainActivityViewModel::class.java -> MainActivityViewModel(
            repository = RepositoryHolder.repository()
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T

}
