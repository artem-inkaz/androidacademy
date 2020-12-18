package com.example.androidacademy.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidacademy.R

class FragmentMoviesDetails :Fragment(R.layout.fragment_movie_details){

    private var listener: FragmentMoviesDetailsBackClickListener? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //возвращаемся обратно по клику, где написано Back
        view?.findViewById<View>(R.id.top_menu_bg)?.apply {
            setOnClickListener {
                listener?.onMovieListBack()
            }
        }


        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentMoviesDetailsBackClickListener) {
            listener = context
        }
    }
    override fun onStart() {
        super.onStart()
            //возвращаемся обратно по клику, где написано Back
        view?.findViewById<View>(R.id.top_menu_bg)?.apply {
            setOnClickListener {
                listener?.onMovieListBack()
            }
        }
    }

    override fun onDetach() {
        listener = null

        super.onDetach()
    }

    interface FragmentMoviesDetailsBackClickListener {
          fun onMovieListBack()
    }

//    companion object {
//        fun create() = FragmentMoviesDetails()
//    }
}