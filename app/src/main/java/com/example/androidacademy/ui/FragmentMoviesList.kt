package com.example.androidacademy.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademy.ChangeFragment
import com.example.androidacademy.R
import com.example.androidacademy.adapter.MovieAdapterViewholder
import com.example.androidacademy.adapter.OnRecyclerMovieClickListener
import com.example.androidacademy.data.Database_movies
import com.example.androidacademy.model.Movie

const val  GRID_LAYOUT_ROW_COUNT = 2

class FragmentMoviesList :Fragment(){

    private var recycler: RecyclerView? = null
    private var changeFragment: ChangeFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            recycler = view.findViewById(R.id.rv_movie_list)
            recycler?.layoutManager = GridLayoutManager(activity, GRID_LAYOUT_ROW_COUNT)
            recycler?.adapter = MovieAdapterViewholder(moviesclickListener)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        changeFragment = context as? ChangeFragment
    }
    override fun onStart() {
        updateData()
        super.onStart()

   }

    override fun onDetach() {
        super.onDetach()
        changeFragment = null
    }

    private fun updateData() {
        (recycler?.adapter as? MovieAdapterViewholder)?.
        bindMovie(Database_movies().getMovies())
    }

    private val moviesclickListener = object : OnRecyclerMovieClickListener {
        override fun onClick(movie: Movie) {
            recycler?.let { rv ->
                Log.d("Parcel", "move.name = ${movie.movieName}")
                changeFragment?.gotoFragmentMoviesDetails(movie)

          }
        }
    }


    companion object {
        fun create() = FragmentMoviesList()
    }
}
