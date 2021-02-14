package com.example.androidacademy.db.entities

import androidx.room.*

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntities)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntities>)

    @Update
    suspend fun update(movie: MovieEntities)

    @Delete
    suspend fun delete(movie: MovieEntities)

    @Query("DELETE FROM MOVIE")
    suspend fun deleteAll()

    @Query("SELECT * FROM MOVIE ORDER BY _id ASC")
    suspend fun getAll(): List<MovieEntities>
}