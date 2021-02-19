package com.example.androidacademy.moviesdetails

import android.content.Intent
import android.provider.CalendarContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidacademy.api.MoviesApi
import com.example.androidacademy.api.convertActorDtoToDomain
import com.example.androidacademy.data.Actor
import com.example.androidacademy.db.entities.MoviesRepositoryImpl
import com.example.androidacademy.movieslist.MoviesListViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class MoviesDetailsViewModel(
    private val apiService: MoviesApi,
    private val repository: MoviesRepositoryImpl
) : ViewModel() {

    private val _actors = MutableLiveData<List<Actor>>()
    val actors: LiveData<List<Actor>> get() = _actors

    private var _calendarIntent = MutableLiveData<Intent>()
    val calendarIntent: LiveData<Intent> get() = _calendarIntent

    fun getActors(movieId: Int) {
        viewModelScope.launch {
            loadActorsFromDb(movieId)
            loadActorsFromApi(movieId)
        }
    }

    private suspend fun loadActorsFromApi(movieId: Int) {
        viewModelScope.launch {
            try {
                // get actors
                val resultRequest = apiService.getActors(movieId = movieId)
                // get actors domain data
                val actors = convertActorDtoToDomain(resultRequest.actors)

                _actors.value = actors

                // do not rewrite with empty data
                if (!actors.isNullOrEmpty()) {
      //              saveActorsLocally(movieId)
                    repository.rewriteActorsByMovieIntoDB(actors, movieId)
                }

            } catch (e: Exception) {
                Log.e(
                    MoviesDetailsViewModel::class.java.simpleName,
                    "Error grab actors data ${e.message}"
                )
            }
        }
    }

    private suspend fun saveActorsLocally(movieId: Int) {
        if (!actors.value.isNullOrEmpty()) {
            viewModelScope.launch {
                repository.rewriteActorsByMovieIntoDB(actors.value!!, movieId)
            }
        }
    }

    private suspend fun loadActorsFromDb(movieId: Int) {
        viewModelScope.launch {
            try {
                // load actors from database
                val actorsDB = repository.getAllActorsByMovie(movieId)

                if (actorsDB.isNotEmpty()) {
                    _actors.value = actorsDB
                }

            } catch (e: Exception) {
                Log.e(
                    MoviesListViewModel::class.java.simpleName,
                    "Error grab actors data from DB: ${e.message}"
                )
            }
        }
    }

    fun scheduleMoveIntoCalendar(movieName: String, dateAndTime: Calendar) {
        val intent = Intent(Intent.ACTION_INSERT)

        with(intent) {
            type = "vnd.android.cursor.item/event"
            putExtra(CalendarContract.Events.TITLE, movieName)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, dateAndTime.timeInMillis)

            putExtra(CalendarContract.Events.ALL_DAY, true)
            putExtra(CalendarContract.Events.STATUS, 1)
            putExtra(CalendarContract.Events.VISIBLE, 1)
            putExtra(CalendarContract.Events.HAS_ALARM, 1)
        }

        _calendarIntent.value = intent
    }

    fun scheduleMoveDone() {
        _calendarIntent.value = null
    }
}