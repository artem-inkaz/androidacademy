package com.example.androidacademy.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes

data class Movie (

  //  val id: Int,
//    @DrawableRes val imageRes: Int
    @DrawableRes val movie_list_picture: Int,
    val pg_name: String,
    @DrawableRes val like: Int,
    val  tagLineTV: String,
    val movieRatingBar:Float,
    val reviewTV: String,
    val movieName: String,
    val txtView_time: String,
    val story: String,
    @DrawableRes val detail_picture: Int,
)
