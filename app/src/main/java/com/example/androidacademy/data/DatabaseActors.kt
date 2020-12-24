package com.example.androidacademy.data

import com.example.androidacademy.R
import com.example.androidacademy.model.Actor

class DatabaseActors {
    fun getActors(): List<Actor> {
        return listOf(
            Actor(R.drawable.movie_downey, "Robert Downey Jr."),
            Actor(R.drawable.movie_evans, "Chris Evans"),
            Actor(R.drawable.movie_ruffalo, "Mark Ruffalo"),
            Actor(R.drawable.movie_hemsworth, "Chris Hemsworth")
        )
    }
}