package com.example.androidacademy.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val ratings: Float,
    val minimumAge: Int,
    val runtime: Int,
    val reviews: Int,
    val genres: List<Genre>,
    val actors: List<Actor>
   // val like: Boolean
) : Parcelable