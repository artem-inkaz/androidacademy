package com.example.androidacademy.moviesdetails

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.androidacademy.ChangeFragment
import com.example.androidacademy.R
import com.example.androidacademy.adapter.ActorAdapterViewholder
import com.example.androidacademy.data.Actor
import com.example.androidacademy.data.Movie
import java.util.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class FragmentMoviesDetails : Fragment() {

    private var changeFragment: ChangeFragment? = null
    private lateinit var adapter: ActorAdapterViewholder
    private var recycler: RecyclerView? = null
    // view model
    private lateinit var viewModel: MoviesDetailsViewModel
    // private var movie: Movie? = null
    // permission
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    // calendar
    private var dateAndTime = Calendar.getInstance()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        var scheduleMovie: Button? = null
        scheduleMovie = view?.findViewById<Button>(R.id.scheduleMovie)?.apply {
            setOnClickListener {
                scheduleIntoCalendar()
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

            movie.let {
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

        // observe write into calendar intent
        viewModel.calendarIntent.observe(viewLifecycleOwner, { calendarIntet ->
            if (calendarIntet != null) {
                startActivity(calendarIntet)
                viewModel.scheduleMoveDone()
            }
        })
    }

    private fun setActorsData(actors: List<Actor>) {
        if (actors.isNotEmpty()) {
            (recycler?.adapter as ActorAdapterViewholder).bindActors(actors)
        }
    }
    @SuppressLint("MissingPermission")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        changeFragment = context as? ChangeFragment
        // permission
        requestPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                onWriteCalendarPermissionGranted()
            }
        }

    }

    override fun onDetach() {
        super.onDetach()
        changeFragment = null
        requestPermissionLauncher.unregister()
    }

    /** start schedule dialog */
    private fun scheduleIntoCalendar() {
        activity?.let {
            // if we have permission
            if (ContextCompat.checkSelfPermission(it, Manifest.permission.WRITE_CALENDAR)
                    == PackageManager.PERMISSION_GRANTED
            ) {
                onWriteCalendarPermissionGranted()
            } else showLocationPermissionExplanationDialog()
        }
    }

    private fun launchDatePicker() {
        DatePickerDialog(
                requireContext(),
                { _, year, monthOfYear, dayOfMonth ->
                    // fix date
                    dateAndTime.set(year, monthOfYear, dayOfMonth)
                    // ask time
                    launchTimePicker()
                },
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun launchTimePicker() {
        TimePickerDialog(
                requireContext(), { _, hour, minute ->
            // fix time
            dateAndTime.set(
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH),
                    hour,
                    minute
            )
            // start calendar intent
            val title = view?.findViewById(R.id.movieName) as TextView
            viewModel.scheduleMoveIntoCalendar(title.text.toString(), dateAndTime)
        },
                dateAndTime.get(Calendar.HOUR),
                dateAndTime.get(Calendar.MINUTE),
                true
        ).show()
    }

    @RequiresPermission(Manifest.permission.WRITE_CALENDAR)
    private fun onWriteCalendarPermissionGranted() {
        // grab date and write movie
        launchDatePicker()
    }

    private fun showLocationPermissionExplanationDialog() {
        context?.let {
            AlertDialog.Builder(it)
                    .setMessage(R.string.permission_ask_write_calendar)
                    .setPositiveButton(R.string.permission_grant) { dialog, _ ->
                        requestWriteCalendarPermission()
                        dialog.dismiss()
                    }
                    .setNegativeButton(R.string.permission_denied) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
        }
    }

    private fun requestWriteCalendarPermission() {
        context?.let {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_CALENDAR)
        }
    }

    companion object {
        private val imageOption = RequestOptions()
            .placeholder(R.drawable.ic_combined_shape)
            .fallback(R.drawable.ic_combined_shape)
            .centerCrop()
    }
}

