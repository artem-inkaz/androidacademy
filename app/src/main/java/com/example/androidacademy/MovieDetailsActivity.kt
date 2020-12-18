package com.example.androidacademy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidacademy.ui.FragmentMoviesList

class MovieDetailsActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_movies_list)
    }
}