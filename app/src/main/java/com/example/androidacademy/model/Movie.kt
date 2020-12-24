package com.example.androidacademy.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes

data class Movie (

//    @DrawableRes val imageRes: Int
    @DrawableRes val movieListPicture: Int,
    val pgName: String,
    @DrawableRes val like: Int,
    val tagLineTV: String,
    val movieRatingBar:Float,
    val reviewTV: String,
    val movieName: String,
    val txtViewTime: String,
    val story: String,
    @DrawableRes val detailPicture: Int,
)
