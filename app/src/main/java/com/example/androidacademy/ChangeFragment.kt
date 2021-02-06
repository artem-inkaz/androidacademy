package com.example.androidacademy

import com.example.androidacademy.data.Movie

interface ChangeFragment {
   fun backFragmentMoviesList()
   fun gotoFragmentMoviesDetails(movie: Movie)
}