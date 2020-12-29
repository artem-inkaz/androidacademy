package com.example.androidacademy.movieslist

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidacademy.State
import com.example.androidacademy.data.Movie
import com.example.androidacademy.data.loadMovies
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class FragmentMoviesViewModel(private val context: Context): ViewModel() {

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
                delay(3000)
                _state.value = State.Loading()
            val movieList = loadMovies(context)
            _mutableLiveDataMovies.value = movieList
                _state.value = State.Success()
            } catch (e: Exception){
                _state.value = State.Error()
                Log.e(ViewModel::class.java.simpleName,"Error grab movies data ${e.message}")
            }
        }
    }


}

