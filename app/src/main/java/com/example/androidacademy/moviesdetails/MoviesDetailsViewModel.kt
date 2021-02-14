package com.example.androidacademy.moviesdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidacademy.api.MoviesApi
import com.example.androidacademy.api.convertActorDtoToDomain
import com.example.androidacademy.data.Actor
import kotlinx.coroutines.launch
import java.lang.Exception

class MoviesDetailsViewModel(private val apiService: MoviesApi) : ViewModel() {

    private val _actors = MutableLiveData<List<Actor>>()
    val actors: LiveData<List<Actor>> get() = _actors

    fun getActors(movieId: Int) {
        viewModelScope.launch {
            try {
                // get actors
                val resultRequest = apiService.getActors(movieId = movieId)
                // get actors domain data
                val actors = convertActorDtoToDomain(resultRequest.actors)

                _actors.value = actors

            } catch (e: Exception) {
                Log.e(
                    MoviesDetailsViewModel::class.java.simpleName,
                    "Error grab actors data ${e.message}"
                )
            }
        }
    }
}