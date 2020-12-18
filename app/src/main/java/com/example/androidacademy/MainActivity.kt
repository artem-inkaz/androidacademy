package com.example.androidacademy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidacademy.ui.FragmentMoviesDetails
import com.example.androidacademy.ui.FragmentMoviesList

class MainActivity : AppCompatActivity(),
    FragmentMoviesList.FragmentMovieListClickListener,
    FragmentMoviesDetails.FragmentMoviesDetailsBackClickListener
{
//    private val FragmentMoviesList =
//        FragmentMoviesList().apply { setClickListener(this@MainActivity) }
//    private val FragmentMoviesDetails =
//        FragmentMoviesDetails().apply { setClickListener(this@MainActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            gotoFragmentMoviesList()
        }
    }
        //открываем фрагмент MovieDetailsFragment когда кликаем по записи фильма
    override fun onMovieListSelected() {
        gotoFragmentMoviesDetails()
    }
    //переходим обратно к предыдущему фрагменту
    override fun onMovieListBack() {
        gotoBack()
    }
        // отображение фрагмента FragmentMoviesList.kt
    private fun gotoFragmentMoviesList() {
        supportFragmentManager.beginTransaction()
            .apply {
                add(R.id.FrameLayoutMain, FragmentMoviesList())
                addToBackStack(null)
                commit()
            }
    }
    // отображение фрагмента FragmentMoviesDetails.kt
    private fun gotoFragmentMoviesDetails() {
        supportFragmentManager.beginTransaction()
            .apply {
                add(R.id.FrameLayoutMain, FragmentMoviesDetails())
                addToBackStack(null)

                commit()
            }
    }
        // Возвращаемся обратно
    private fun  gotoBack() {
        supportFragmentManager.popBackStack()
    }
}