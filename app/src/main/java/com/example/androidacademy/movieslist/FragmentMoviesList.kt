package com.example.androidacademy.movieslist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademy.ChangeFragment
import com.example.androidacademy.R
import com.example.androidacademy.adapter.OnRecyclerMovieClickListener
import com.example.androidacademy.data.Movie
import com.example.androidacademy.State
import com.example.androidacademy.adapter.MovieAdapter

class FragmentMoviesList :Fragment(){

    private var recycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var changeFragment: ChangeFragment? = null

    private val viewModel: FragmentMoviesViewModel by viewModels { MoviesViewModelFactory(requireContext()) }

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
        recycler?.adapter = MovieAdapter(moviesclickListener)

        setObservers()

    }

    override fun onStart() {
      super.onStart()
//      val viewModel = ViewModelProvider(this).get(FragmentMoviesViewModel::class.java)
//        viewModel.updateData()
    }

    private fun setObservers() {
        // observe movies data
        viewModel.listMovies.observe(viewLifecycleOwner, { movieList ->
            (recycler!!.adapter as MovieAdapter).apply {
                bindMovie(movieList)
            }
        })

        // observe status
        viewModel.state.observe(viewLifecycleOwner, { status ->
            when (status) {
                is State.Init, is State.Success -> {
                    progressBar?.visibility = View.INVISIBLE
                }
                is State.Loading -> {
                    progressBar?.visibility = View.VISIBLE
                }
                is State.Error -> {
                    progressBar?.visibility = View.INVISIBLE
                }
            }
        })
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
        changeFragment = null

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




