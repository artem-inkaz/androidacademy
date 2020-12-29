package com.example.androidacademy.movieslist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class MoviesViewModelFactory (private val context: Context)  : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        FragmentMoviesViewModel::class.java -> FragmentMoviesViewModel(context)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}