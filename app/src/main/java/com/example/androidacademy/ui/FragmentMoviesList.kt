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
import com.example.androidacademy.data.Movie
import com.example.androidacademy.data.loadMovies
import kotlinx.coroutines.*

class FragmentMoviesList :Fragment(){

    private var recycler: RecyclerView? = null
    private var changeFragment: ChangeFragment? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(FragmentMoviesList::class.java.simpleName,"CoroutineException: $exception")
    }

    private var scope = CoroutineScope(
        SupervisorJob() +
                Dispatchers.IO +
                exceptionHandler
    )
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

        updateData()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        changeFragment = context as? ChangeFragment
    }

    override fun onDetach() {
        super.onDetach()
        changeFragment = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.cancel()

    }

    private fun updateData() {
        var moviesList: List<Movie>? = null
        scope.launch {
            moviesList = loadMovies(requireContext())
            (recycler?.adapter as? MovieAdapterViewholder)?.apply {
            moviesList?.let { bindMovie(it) }
            }
        }
    }
    private val moviesclickListener = object : OnRecyclerMovieClickListener {
        override fun onClick(movie: Movie) {
                Log.d("Parcel", "move.name = ${movie.title}")
                changeFragment?.gotoFragmentMoviesDetails(movie)
        }
    }

    companion object {
        const val  GRID_LAYOUT_ROW_COUNT = 2
    }
}
