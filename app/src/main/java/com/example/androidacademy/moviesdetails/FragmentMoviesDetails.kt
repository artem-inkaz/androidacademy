package com.example.androidacademy.moviesdetails


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.androidacademy.ChangeFragment
import com.example.androidacademy.R
import com.example.androidacademy.adapter.ActorAdapterViewholder
import com.example.androidacademy.data.Actor
import com.example.androidacademy.data.Movie


class FragmentMoviesDetails :Fragment(){

    private var changeFragment: ChangeFragment? = null
    private lateinit var adapter: ActorAdapterViewholder

    private var recycler : RecyclerView? =  null
    // view model
    private lateinit var viewModel: MoviesDetailsViewModel

    private var movie: Movie? = null

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {

      //  movie = FragmentMoviesDetailsArgs.fromBundle(requireArguments()).selectedMovie

        val viewModelFactory = MoviesDetailViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MoviesDetailsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_movie_details, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       recycler = view.findViewById(R.id.rv_foto_actors)
        adapter = ActorAdapterViewholder()
        recycler?.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recycler?.adapter = adapter

        var btnBack: TextView? = null
        btnBack = view.findViewById<TextView>(R.id.back_text).apply {
            setOnClickListener {
                changeFragment?.backFragmentMoviesList()
            }
        }

        val backdrop = view.findViewById(R.id.topmovieImage) as ImageView
        val title = view.findViewById(R.id.movieName) as TextView
        val reviewTV = view.findViewById(R.id.reviewTV) as TextView
        val genres = view.findViewById(R.id.tagLineTV) as TextView
        val ratings = view.findViewById(R.id.movieRatingBar) as RatingBar
        val overview = view.findViewById(R.id.story_descriptionTV) as TextView

            arguments?.getParcelable<Movie>(Movie::class.java.simpleName)?.let { movie ->
                Glide.with(requireContext())
                        .load(movie.backdrop)
                        .apply(imageOption)
                        .into(backdrop)

            title.text = movie.title
            ratings.rating = movie.ratings / 2
            title.text = movie.title
            reviewTV.text = "" + movie.reviews + " MIN"
            genres.text = movie.genres.joinToString(", ")
            overview.text = movie.overview

                movie?.let {
                    viewModel.getActors(it.id)
                }
        }
        setObservers()


    }

    private fun setObservers() {
        // observe actors data
        viewModel.actors.observe(viewLifecycleOwner, {
            setActorsData(it)
        })
    }

    private fun setActorsData(actors: List<Actor>) {

        if (actors.isNotEmpty()) {
            (recycler?.adapter as ActorAdapterViewholder).bindActors(actors)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        changeFragment= context as? ChangeFragment
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

