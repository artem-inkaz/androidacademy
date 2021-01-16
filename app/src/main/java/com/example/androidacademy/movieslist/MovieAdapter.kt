package com.example.androidacademy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademy.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.androidacademy.data.Movie

class MovieAdapter(
        private var moviesclickListener: OnRecyclerMovieClickListener) : RecyclerView.Adapter<MovieViewHolder>() {

    private var moviesList = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder = MovieViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_holder_movie, parent, false))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(moviesList[position])
        holder.itemView.setOnClickListener {
            moviesclickListener.onClick(moviesList[position])
        }
    }

    override fun getItemCount(): Int = moviesList.size

    fun bindMovie(newMoviesList: List<Movie>) {
        moviesList = newMoviesList
        notifyDataSetChanged()
    }
}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        private val imageOption = RequestOptions()
                .placeholder(R.drawable.ic_combined_shape)
                .fallback(R.drawable.ic_combined_shape)
                .centerCrop()
    }

    private val poster: ImageView = itemView.findViewById(R.id.movie_list_picture)
    private val movieName: TextView = itemView.findViewById(R.id.movieName)
    private val pgName: TextView = itemView.findViewById(R.id.pg_name)
    private val reviewTV: TextView = itemView.findViewById(R.id.reviewTV)
    private val genres: TextView = itemView.findViewById(R.id.tagLineTV)
    private val runtime: TextView = itemView.findViewById(R.id.txtView_time)
    private val ratings: RatingBar = itemView.findViewById(R.id.movieRatingBar)
    private val like: ImageView = itemView.findViewById(R.id.imgView_like)

    fun onBind(movie: Movie) {
        Glide.with(itemView.context)
                .load(movie.poster)
                .apply(imageOption)
                .into(poster)

        ratings.rating = movie.ratings / 2
        movieName.text = movie.title
        //pgName.text = movie.minimumAge.toString()
        reviewTV.text = "" + movie.reviews + " MIN"
      //  genres.text = movie.genres.joinToString(", ") { it.name }
        genres.text = movie.genres.joinToString(", ")
        runtime.text = " " + movie.runtime + " MIN"
    }

}

interface OnRecyclerMovieClickListener {
    fun onClick(movie: Movie)
}

