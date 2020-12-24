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
import com.bumptech.glide.request.RequestOptions
import com.example.androidacademy.ChangeFragment
import com.example.androidacademy.R
import com.example.androidacademy.adapter.ActorAdapterViewholder
import com.example.androidacademy.data.DatabaseActors
import com.example.androidacademy.model.Movie

class FragmentMoviesDetails :Fragment(R.layout.fragment_movie_details){

    private var changeFragment: ChangeFragment? = null
    private lateinit var adapter: ActorAdapterViewholder
    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //возвращаемся обратно по клику, где написано Back
//        view?.findViewById<View>(R.id.top_menu_bg)?.apply {
//            setOnClickListener {
//                changeFragment?.backFragmentMoviesList()
//            }
//        }
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
         val detail_picture= view.findViewById(R.id.topmovieImage) as ImageView
         val movieName= view.findViewById(R.id.movieName)as TextView
         val pg_name= view.findViewById(R.id.pg_name)as TextView
         val reviewTV= view.findViewById(R.id.reviewTV)as TextView
         val tagLineTV= view.findViewById(R.id.tagLineTV)as TextView
         val movieRatingBar= view.findViewById(R.id.movieRatingBar) as RatingBar
         val story= view.findViewById(R.id.story_descriptionTV)as TextView
         val imageOption = RequestOptions()
            .placeholder(R.drawable.ic_combined_shape)
            .fallback(R.drawable.ic_combined_shape)
            .centerCrop()

 //       val bundle = arguments
        if (arguments != null) {
            Glide.with(context)
                .load(movie?.detailPicture)
                .apply(imageOption)
                .into(detail_picture)

            detail_picture.setImageResource(movie?.movieListPicture)
            movieName.setText(movie?.movieName)
            pg_name.setText(movie?.pgName)
            reviewTV.setText(movie?.reviewTV)
            tagLineTV.setText(movie?.tagLineTV)
            story.setText(movie?.story)
        }
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
        adapter.bindActors(DatabaseActors().getActors())
        adapter.notifyDataSetChanged()
    }

    override fun onDetach() {
        super.onDetach()
        changeFragment = null

    }


}