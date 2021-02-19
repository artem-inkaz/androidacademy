package com.example.androidacademy.db.entities

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidacademy.App

@Database(
    entities = [MovieEntities::class, ActorsEntites::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    abstract fun actorsDao(): ActorsDao

    companion object {
        val instance: MoviesDatabase by lazy {
            Room.databaseBuilder(
                App.context(),
                MoviesDatabase::class.java,
                DbContract.DATABASE_NAME
            ).build()
        }
    }
}