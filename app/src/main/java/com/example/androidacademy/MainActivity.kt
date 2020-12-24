package com.example.androidacademy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.androidacademy.data.Movie
import com.example.androidacademy.ui.FragmentMoviesDetails
import com.example.androidacademy.ui.FragmentMoviesList

class MainActivity : AppCompatActivity(), ChangeFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            gotoFragmentMoviesList()
        }
    }

        // отображение фрагмента FragmentMoviesList.kt
        private fun gotoFragmentMoviesList() {

            supportFragmentManager.commit {
                add<FragmentMoviesList>(R.id.frame_layout_main)
            }
//        supportFragmentManager.beginTransaction()
//            .apply {
//                add(R.id.frame_layout_main, FragmentMoviesList())
//                addToBackStack(null)
//                commit()
//            }
    }

    // отображение фрагмента FragmentMoviesDetails.kt
    override fun gotoFragmentMoviesDetails(movie: Movie) {
        val bundle = Bundle().apply {
            putParcelable(Movie::class.java.simpleName, movie)
        }
//        val fragment = FragmentMoviesDetails()
//        supportFragmentManager.beginTransaction()
//            .apply {
//                add(R.id.frame_layout_main, fragment)
//                addToBackStack(null)
//                commit()
//            }
        supportFragmentManager.commit {
            add<FragmentMoviesDetails>(containerViewId = R.id.frame_layout_main, args = bundle)
            addToBackStack(null)
        }
    }

       // Возвращаемся обратно
       override fun backFragmentMoviesList() {
        supportFragmentManager.popBackStack()
    }
}