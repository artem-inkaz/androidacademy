package com.example.androidacademy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.example.androidacademy.backgroundworkmanager.UpdateMovieWorkerRequest
import com.example.androidacademy.data.Movie
import com.example.androidacademy.db.entities.MoviesRepository
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: MoviesRepository) : ViewModel()  {

    private val backgroundRequests = UpdateMovieWorkerRequest()

    /** check new Movie from background */
    fun startBackgroundMovieCheck(){
        WorkManager.getInstance(App.context()).enqueueUniquePeriodicWork(
            UpdateMovieWorkerRequest.WORKER_MOVIE_UPDATE_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            backgroundRequests.periodicRequestCoroutine
        )
    }

    /** when intent returns to app from notification -  show selected movie*/
//    fun showMovieFromNotification(movieId: Long){
//        // check Movie in DB
//        viewModelScope.launch {
//            val movie = repository.getMovieById(movieId)
//            _navigateToSelectedMovie.value = movie
//        }
//    }

    /** set to null after navigation to avoid false jumps*/
//    fun showMovieFromNotificationComplete() {
//        _navigateToSelectedMovie.value = null
//    }
}