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

class MovieAdapterViewholder(
        private var moviesclickListener: OnRecyclerMovieClickListener
        ) : RecyclerView.Adapter<MovieViewHolder>() {

        private var moviesList= listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): MovieViewHolder = MovieViewHolder(LayoutInflater
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

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

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
    private val txtViewTime: TextView = itemView.findViewById(R.id.txtView_time)
    private val movieRatingBar: RatingBar = itemView.findViewById(R.id.movieRatingBar)
    private val like: ImageView = itemView.findViewById(R.id.imgView_like)

    fun onBind(movie: Movie) {
        Glide.with(itemView.context)
                .load(movie.poster)
                .apply(imageOption)
                .into(poster)

 //       movie_list_picture.setImageResource(movie.movie_list_picture)
   //     like.setImageResource(movie.like)
        movieRatingBar.rating = movie.ratings
        movieName.text = movie.title
    //    pgName.text = movie.pgName
     //   reviewTV.text = movie.review
        genres.text = movie.genres.joinToString(", ") { it.name }
        txtViewTime.text ="{movie.runtime} min"
    }

}

//private val RecyclerView.ViewHolder.context
//    get() = this.itemView.context

interface OnRecyclerMovieClickListener{
    fun onClick(movie: Movie)
}

