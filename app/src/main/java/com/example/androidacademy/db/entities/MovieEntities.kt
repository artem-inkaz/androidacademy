package com.example.androidacademy.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DbContract.MovieContract.TABLE_NAME)
data class MovieEntities(
    @PrimaryKey
    @ColumnInfo(name = DbContract.MovieContract.COLUMN_NAME_ID)
    val id: Long,
    val title: String,
    val overview: String?,
    val poster: String?,
    val backdrop: String?,
    val ratings: Float,
    val adult: Boolean,
    val runtime: Int?,
    val reviews: Int,
    val genres: String,
    val like: Boolean = false
)