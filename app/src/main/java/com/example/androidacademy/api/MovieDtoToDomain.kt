package com.example.androidacademy.api

import com.example.androidacademy.BuildConfig
import com.example.androidacademy.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun convertMovieDtoToDomain(
    moviesDto: List<MovieDto>,
    genres: List<GenreDto>
): List<Movie> =
    withContext(Dispatchers.Default) {
        val genresMap: Map<Int, GenreDto> = genres.associateBy { it.id }

        moviesDto.map { movieDto: MovieDto ->
            Movie(
                id = movieDto.id,
                title = movieDto.title,
                overview = movieDto.overview,
                poster = movieDto.poster?.let { BuildConfig.BASE_IMAGE_URL + movieDto.poster },
                backdrop = movieDto.backdrop?.let { BuildConfig.BASE_IMAGE_URL + movieDto.backdrop },
                ratings = movieDto.ratings / 2,
                adult = movieDto.adult,
                runtime = movieDto.runtime,
                reviews = movieDto.reviews,
                genres = movieDto.genreIds.map {
                    genresMap[it]?.name.toString()
                }
            )
        }
    }


