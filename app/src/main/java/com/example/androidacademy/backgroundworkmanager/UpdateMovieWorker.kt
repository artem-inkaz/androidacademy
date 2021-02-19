package com.example.androidacademy.backgroundworkmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.androidacademy.api.MoviesApi
import com.example.androidacademy.api.RetrofitModule
import com.example.androidacademy.api.convertMovieDtoToDomain
import com.example.androidacademy.data.Movie
import com.example.androidacademy.notifiactions.MovieNotifications
import com.example.androidacademy.repositories.RepositoryHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.create

class UpdateMovieWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    @Suppress("EXPERIMENTAL_API_USAGE")
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val api = RetrofitModule.retrofit.create<MoviesApi>()
                val repository = RepositoryHolder.repository()
                // get genres
                val genres = api.getGenres()
                // get movie
                val moviesDto = api.getMovies()
                // get movie domain data
                val movies = convertMovieDtoToDomain(moviesDto.results, genres.genres)

                // get old movies from DB
                val oldMovies = repository.getAllMovies()

                // don't rewrite with empty data
                if (!movies.isNullOrEmpty()) {
                    repository.rewriteMoviesListIntoDB(movies)
                }

                // if we have any movies - find new best movie and show notification
                if (!movies.isNullOrEmpty() || !oldMovies.isNullOrEmpty()) {
                    checkNewMoviesForNotification(oldMovies, movies)
                }
                Result.success()
            } catch (e: Exception) {
                Result.failure()

            }
        }
    }

    private fun checkNewMoviesForNotification(oldMovies: List<Movie>, movies: List<Movie>?) {
        // find new best movie or just best movie
        val movie = movies?.subtract(oldMovies)?.maxByOrNull { it.ratings }
                ?: oldMovies.maxByOrNull { it.ratings }

        if (movie != null) {
            sayNotification(movie)
        }
    }

    private fun sayNotification(movie: Movie) {
        val notifications = MovieNotifications(context)
        notifications.initialize()
        notifications.showNotification(movie)
    }



}