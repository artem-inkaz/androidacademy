package com.example.androidacademy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.androidacademy.backgroundworkmanager.UpdateMovieWorkerRequest
import com.example.androidacademy.data.Movie
import com.example.androidacademy.moviesdetails.FragmentMoviesDetails
import com.example.androidacademy.movieslist.FragmentMoviesList
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.example.androidacademy.backgroundworkmanager.UpdateMovieWorkerRequest.Companion.WORKER_MOVIE_UPDATE_NAME

class MainActivity : AppCompatActivity(), ChangeFragment {

    private val backgroundRequests = UpdateMovieWorkerRequest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            gotoFragmentMoviesList()
        }

        WorkManager.getInstance(App.context()).enqueueUniquePeriodicWork(
            WORKER_MOVIE_UPDATE_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            backgroundRequests.periodicRequestCoroutine
        )
    }
    // отображение фрагмента FragmentMoviesList.kt
    private fun gotoFragmentMoviesList() {

        supportFragmentManager.commit {
            add<FragmentMoviesList>(R.id.frame_layout_main)
        }
    }
    // отображение фрагмента FragmentMoviesDetails.kt
    override fun gotoFragmentMoviesDetails(movie: Movie) {
        val bundle = Bundle().apply {
            putParcelable(Movie::class.java.simpleName, movie)
        }
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