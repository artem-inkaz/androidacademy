package com.example.androidacademy

import com.example.androidacademy.model.Movie

interface ChangeFragment {
   fun backFragmentMoviesList()
   fun gotoFragmentMoviesDetails(movie: Movie)

}