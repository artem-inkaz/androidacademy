package com.example.androidacademy.repositories

import com.example.androidacademy.db.entities.MoviesRepositoryImpl

object RepositoryHolder {

    private val repository by lazy { MoviesRepositoryImpl() }
    fun repository(): MoviesRepositoryImpl = repository
}

