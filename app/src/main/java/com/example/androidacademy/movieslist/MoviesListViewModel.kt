package com.example.androidacademy.movieslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidacademy.State
import com.example.androidacademy.api.MoviesApi
import com.example.androidacademy.api.convertMovieDtoToDomain
import com.example.androidacademy.data.Movie
import com.example.androidacademy.db.entities.MoviesRepository
import kotlinx.coroutines.*
import java.lang.Exception

class MoviesListViewModel(
    private val apiService: MoviesApi,
    private val repository: MoviesRepository
) : ViewModel() {

    private val _state = MutableLiveData<State>(State.Init)
    val state: LiveData<State> get() = _state
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    fun loadMovies() {
        viewModelScope.launch {
            loadMoviesFromDb()
            loadMoviesFromApi()
        }
    }

    private suspend fun loadMoviesFromApi() {
        viewModelScope.launch {
            try {
                // if we got movies from db - don't change state
                if (state.value != State.Success) {
                    _state.value = State.Loading
                }

                // get genres
                val genres = apiService.getGenres()
                // get movie
                val moviesDto = apiService.getMovies()
                // get movie domain data
                val movies = convertMovieDtoToDomain(moviesDto.results, genres.genres)

                _movies.value = movies
                _state.value = State.Success

                // don't rewrite with empty data
                if (!movies.isNullOrEmpty()) {
                    saveMoviesLocally()
                }

            } catch (e: Exception) {
                // if we didn't receive data from DB before - show error connection
                if (state.value != State.Success) {
                    _state.value = State.Error
                }
                // log error anyway
                Log.e(
                    MoviesListViewModel::class.java.simpleName,
                    "Error grab movies data from API: ${e.message}"
                )
            }
        }
    }

    private suspend fun saveMoviesLocally() {
        if (!movies.value.isNullOrEmpty()) {
            viewModelScope.launch {
                repository.rewriteMoviesListIntoDB(movies.value!!)
            }
        }
    }

    private suspend fun loadMoviesFromDb() {
        viewModelScope.launch {
            try {
                _state.value = State.Loading

                // load movies from database
                val moviesDB = repository.getAllMovies()

                // if there are any movies - show them and show success state
                if (moviesDB.isNotEmpty()) {
                    _movies.value = moviesDB
                    _state.value = State.Success
                } else {
                    _state.value = State.EmptyDataSet
                }

            } catch (e: Exception) {
                _state.value = State.EmptyDataSet
                Log.e(
                    MoviesListViewModel::class.java.simpleName,
                    "Error grab movies data from DB: ${e.message}"
                )
            }
        }
    }

}

