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
import com.example.androidacademy.data.Movie


class FragmentMoviesDetails :Fragment(){

    private var changeFragment: ChangeFragment? = null
    private lateinit var adapter: ActorAdapterViewholder

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,
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

        val backdrop = view.findViewById(R.id.topmovieImage) as ImageView
        val title = view.findViewById(R.id.movieName) as TextView
        val pgName = view.findViewById(R.id.pg_name) as TextView
        val reviewTV = view.findViewById(R.id.reviewTV) as TextView
        val genres = view.findViewById(R.id.tagLineTV) as TextView
        val ratings = view.findViewById(R.id.movieRatingBar) as RatingBar
        val overview = view.findViewById(R.id.story_descriptionTV) as TextView
        val cast = view.findViewById(R.id.castTV) as TextView

        val bundle = arguments
        if (arguments != null) {
            val movie = bundle?.getParcelable<Movie>(Movie::class.java.simpleName)
            Glide.with(requireContext())
                .load(movie?.backdrop)
                .apply(imageOption)
                .into(backdrop)

            title.text = movie?.title
            //Вопрос: Почему тут ошибка?
            //ratings.rating = movie?.(ratings / 2)
            title.text = movie?.title
            pgName.text = movie?.minimumAge.toString()
            reviewTV.text = ""+movie?.reviews+" MIN"
            genres.text = movie?.genres?.joinToString(", ") { it.name }
            overview.text = movie?.overview
            when {
                movie?.actors?.isNotEmpty() == true -> (movie?.actors?.let { (recycler?.adapter as? ActorAdapterViewholder)?.bindActors(it) })
                else -> cast.visibility = View.INVISIBLE
            }
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

