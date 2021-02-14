package com.example.androidacademy.movieslist

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidacademy.State
import com.example.androidacademy.api.MoviesApi
import com.example.androidacademy.api.convertMovieDtoToDomain
import com.example.androidacademy.data.Movie
//import com.example.androidacademy.data.loadMovies
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

//class MoviesListViewModel(private val context: Context) : ViewModel() {
class MoviesListViewModel(private val apiService: MoviesApi) : ViewModel() {

    private val _state = MutableLiveData<State>(State.Init())
    val state: LiveData<State> get() = _state

    private val _mutableLiveDataMovies = MutableLiveData<List<Movie>>(emptyList())
    val listMovies: LiveData<List<Movie>> get() = _mutableLiveDataMovies

//    init {
//        updateData()
//    }

    fun updateData() {

        viewModelScope.launch {
            try {
                _state.value = State.Loading()
                // get genres
                val genres = apiService.getGenres()
                // get movie
                val moviesDto = apiService.getMovies()
                // get movie domain data
                val movies = convertMovieDtoToDomain(moviesDto.results, genres.genres)

                _mutableLiveDataMovies.value = movies
                _state.value = State.Success()

            } catch (e: Exception) {
                _state.value = State.Error()
                Log.e(ViewModel::class.java.simpleName, "Error grab movies data ${e.message}")
            }
        }
    }


}

