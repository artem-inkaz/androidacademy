package com.example.androidacademy

import android.app.Application
import android.content.Context
import com.example.androidacademy.db.entities.MoviesRepositoryImpl

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        private var context: Context? = null
        fun context(): Context = context ?: throw IllegalStateException()

        private val repository by lazy { MoviesRepositoryImpl() }
        fun repository(): MoviesRepositoryImpl = repository
    }
}
