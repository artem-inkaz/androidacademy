package com.example.androidacademy.db.entities

import com.example.androidacademy.data.Actor
import com.example.androidacademy.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface MoviesRepository {
    /* movies */
    suspend fun getAllMovies(): List<Movie>
    suspend fun writeMovieIntoDB(movie: Movie)
    suspend fun rewriteMoviesListIntoDB(movies: List<Movie>)

    /* actors */
    suspend fun getAllActorsByMovie(movieId: Int): List<Actor>
    suspend fun rewriteActorsByMovieIntoDB(actors: List<Actor>, movieId: Int)
}

class MoviesRepositoryImpl : MoviesRepository {
    private val moviesDB = MoviesDatabase.instance

    /** add movies data into db*/
    override suspend fun writeMovieIntoDB(movie: Movie) = withContext(Dispatchers.IO) {
        moviesDB.moviesDao().insert(toMovieEntity(movie))
    }

    /** del movies and write new movies data set again */
    override suspend fun rewriteMoviesListIntoDB(movies: List<Movie>) =
        withContext(Dispatchers.IO) {
            moviesDB.moviesDao().deleteAll()
            moviesDB.moviesDao().insertAll(movies.map { toMovieEntity(it) })
        }

    /** request movies from db */
    override suspend fun getAllMovies(): List<Movie> = withContext(Dispatchers.IO) {
        moviesDB.moviesDao().getAll().map { toMovieDomain(it) }
    }

    /** request actors by movie id */
    override suspend fun getAllActorsByMovie(movieId: Int): List<Actor> =
        withContext(Dispatchers.IO) {
            moviesDB.actorsDao().getAllByMovieId(movieId).map { toActorDomain(it) }
        }

    /** del actors and write it again. all by movie id */
    override suspend fun rewriteActorsByMovieIntoDB(actors: List<Actor>, movieId: Int) =
        withContext(Dispatchers.IO) {
            moviesDB.actorsDao().deleteByMovieId(movieId)
            moviesDB.actorsDao().insertAll(actors.map { toActorEntity(it, movieId) })
        }

    private fun toActorDomain(actorEntity: ActorsEntites) = Actor(
        id = actorEntity.actorId,
        name = actorEntity.name,
        picture = actorEntity.image
    )

    private fun toActorEntity(actorDomain: Actor, movieId: Int) = ActorsEntites(
        id = null,  // autogenerated primary key
        actorId = actorDomain.id,
        name = actorDomain.name,
        image = actorDomain.picture,
        movie = movieId.toLong()
    )

    private fun toMovieEntity(movieDomain: Movie) = MovieEntities(
        id = movieDomain.id.toLong(),
        title = movieDomain.title,
        overview = movieDomain.overview,
        poster = movieDomain.poster,
        backdrop = movieDomain.backdrop,
        ratings = movieDomain.ratings,
        adult = movieDomain.adult,
        runtime = movieDomain.runtime,
        reviews = movieDomain.reviews,
        genres = movieDomain.genres.joinToString(","),
        like = movieDomain.like
    )

    private fun toMovieDomain(movieEntity: MovieEntities) = Movie(
        id = movieEntity.id.toInt(),
        title = movieEntity.title,
        overview = movieEntity.overview,
        poster = movieEntity.poster,
        backdrop = movieEntity.backdrop,
        ratings = movieEntity.ratings,
        adult = movieEntity.adult,
        runtime = movieEntity.runtime,
        reviews = movieEntity.reviews,
        genres = movieEntity.genres.split(",").map { it.trim() },
        like = movieEntity.like
    )
}