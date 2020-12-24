package com.example.androidacademy.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.androidacademy.ChangeFragment
import com.example.androidacademy.R
import com.example.androidacademy.adapter.ActorAdapterViewholder
import com.example.androidacademy.adapter.MovieAdapterViewholder
import com.example.androidacademy.data.Movie
import com.example.androidacademy.data.loadMovies
import kotlinx.coroutines.launch

class FragmentMoviesDetails :Fragment(R.layout.fragment_movie_details){

    private var changeFragment: ChangeFragment? = null
    private lateinit var adapter: ActorAdapterViewholder
    //private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler: RecyclerView = view.findViewById(R.id.rv_foto_actors)
        adapter = ActorAdapterViewholder()
        recycler.layoutManager =
        LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recycler?.adapter = adapter
        var btnBack: TextView? = null
        btnBack = view.findViewById<TextView>(R.id.back_text).apply {
            setOnClickListener {
                changeFragment?.backFragmentMoviesList()
            }
        }

//        val movie: Movie? = requireArguments().getParcelable(Movie::class.java.simpleName)
//        movie?.let{ setMovieData(it) }
       val movie: List<Movie>? = null
//        setMovieData(it)

        val backdrop: ImageView = view.findViewById(R.id.topmovieImage)
        val title: TextView = view.findViewById(R.id.movieName)
        val pgName: TextView = view.findViewById(R.id.pg_name)
        val reviewTV: TextView = view.findViewById(R.id.reviewTV)
        val genres: TextView =  view.findViewById(R.id.tagLineTV)
        val ratings: RatingBar =  view.findViewById(R.id.movieRatingBar)
        val overview: TextView = view.findViewById(R.id.story_descriptionTV)
        val rv_foto_actors: RecyclerView= view.findViewById(R.id.rv_foto_actors)


        Glide.with(requireContext())
            .load(movie.backdrop)
            //  .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .apply(imageOption)
            .into(backdrop)

        title.text=movie.title
        ratings.rating = movie.ratings/2
        title.text=movie?.title
        pgName.text=movie.minimumAge.toString()
        reviewTV.text="{movie.reviews} MIN"
        genres.text=movie.genres.joinToString(", ") { it.name}
        overview.text=movie.overview
        (rv_foto_actors?.adapter as? ActorAdapterViewholder)?.bindActors(movie.actors)

    }

    // set data on fragment
    private fun setMovieData(movie: Movie) {

       // val backdrop= R.layout.fragment_movie_details
        //val backdrop= R.id.topmovieImage as ImageView
//        val title= R.id.movieName as TextView
//        val pgName= R.id.pg_name as TextView
//        val reviewTV= R.id.reviewTV as TextView
//        val genres=  R.id.tagLineTV as TextView
//        val ratings=  R.id.movieRatingBar as RatingBar
//        val overview= R.id.story_descriptionTV as TextView
//        val rv_foto_actors= R.id.rv_foto_actors as RecyclerView
//
//        Glide.with(requireContext())
//            .load(movie.backdrop)
//            //  .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//            .apply(imageOption)
//            .into(backdrop)
//
//        title.text=movie.title
//        ratings.rating = movie.ratings/2
//        title.text=movie?.title
//        pgName.text=movie.minimumAge.toString()
//        reviewTV.text="{movie.reviews} MIN"
//        genres.text=movie.genres.joinToString(", ") { it.name}
//        overview.text=movie.overview
//        (rv_foto_actors?.adapter as? ActorAdapterViewholder)?.bindActors(movie.actors)

    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        changeFragment= context as? ChangeFragment
    }
    override fun onStart() {
        super.onStart()
            //возвращаемся обратно по клику, где написано Back
        view?.findViewById<View>(R.id.top_menu_bg)?.apply {
            setOnClickListener {
                changeFragment?.backFragmentMoviesList()
            }
        }
        updateData()
    }

    private fun updateData() {
     //   val it
    //    adapter.bindActors(it)
   //     adapter.notifyDataSetChanged()
//        var moviesList: List<Movie>? = null
//        scope.launch {
//            moviesList = loadMovies(requireContext())
//            (recycler?.adapter as? MovieAdapterViewholder)?.apply {
//                moviesList?.let { bindMovie(it) }}
//        }


    }

    override fun onDetach() {
        super.onDetach()
        changeFragment = null

    }

    companion object {
        private val imageOption = RequestOptions()
            .placeholder(R.drawable.ic_combined_shape)
            .fallback(R.drawable.ic_combined_shape)
            .centerCrop()
    }

}