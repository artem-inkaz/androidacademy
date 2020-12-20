package com.example.androidacademy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademy.R
import com.example.androidacademy.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieAdapterViewholder(
        private var moviesclickListener: OnRecyclerMovieClickListener
        ) : RecyclerView.Adapter<MoviesViewHolder>() {

    private var moviesList= listOf<Movie>()

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int):
            MoviesViewHolder = MovieViewHolder(
            LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_holder_movie, parent, false))

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                holder.onBind(moviesList[position])
                holder.itemView.setOnClickListener {
                    moviesclickListener.onClick(moviesList[position])
                }
            }
        }
    }
    override fun getItemCount(): Int = moviesList.size

    fun bindMovie(newMoviesList: List<Movie>) {
        moviesList = newMoviesList
        notifyDataSetChanged()
    }


}

abstract class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class MovieViewHolder(itemView: View) : MoviesViewHolder(itemView){

    companion object {
        private val imageOption = RequestOptions()
            .placeholder(R.drawable.ic_combined_shape)
            .fallback(R.drawable.ic_combined_shape)
            .centerCrop()
    }


    private val movie_list_picture: ImageView = itemView.findViewById(R.id.movie_list_picture)
    private val movieName: TextView = itemView.findViewById(R.id.movieName)
    private val pg_name: TextView = itemView.findViewById(R.id.pg_name)
    private val reviewTV: TextView = itemView.findViewById(R.id.reviewTV)
    private val tagLineTV: TextView = itemView.findViewById(R.id.tagLineTV)
    private val txtView_time: TextView = itemView.findViewById(R.id.txtView_time)
    private val movieRatingBar: RatingBar = itemView.findViewById(R.id.movieRatingBar)
    private val like: ImageView = itemView.findViewById(R.id.imgView_like)

    fun onBind(movie: Movie) {
        Glide.with(itemView.context)
                .load(movie.movie_list_picture)
                .apply(imageOption)
                .into(movie_list_picture)

        movie_list_picture.setImageResource(movie.movie_list_picture)
        like.setImageResource(movie.like)
        movieRatingBar.rating = movie.movieRatingBar
        movieName.text = movie.movieName
        pg_name.text = movie.pg_name
        reviewTV.text = movie.reviewTV
        tagLineTV.text = movie.tagLineTV
        txtView_time.text = movie.txtView_time

    }


}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context

interface OnRecyclerMovieClickListener{
    fun onClick(movie: Movie)
}

