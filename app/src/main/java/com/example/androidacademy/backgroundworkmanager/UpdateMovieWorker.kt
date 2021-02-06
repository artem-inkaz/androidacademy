package com.example.androidacademy.backgroundworkmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.androidacademy.api.MoviesApi
import com.example.androidacademy.api.RetrofitModule
import com.example.androidacademy.api.convertMovieDtoToDomain
import com.example.androidacademy.repositories.RepositoryHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.create

class UpdateMovieWorker(
    context: Context,
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
                // don't rewrite with empty data
                if (!movies.isNullOrEmpty()) {
                    repository.rewriteMoviesListIntoDB(movies)
                }

                Result.success()
            } catch (e: Exception) {
                Result.failure()

            }
        }
    }
}