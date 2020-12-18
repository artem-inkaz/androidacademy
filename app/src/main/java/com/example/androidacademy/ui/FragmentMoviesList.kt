package com.example.androidacademy.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.androidacademy.R

class FragmentMoviesList :Fragment(){

  private  var listener: FragmentMovieListClickListener? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        view?.findViewById<View>(R.id.Layout_movie_list)?.apply {
            setOnClickListener {
                listener?.onMovieListSelected()
            }
        }
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentMovieListClickListener) {
            listener = context
        }
    }

    override fun onStart() {
        super.onStart()

        view?.findViewById<View>(R.id.movie_list_items)?.apply {
            setOnClickListener {
                listener?.onMovieListSelected()
            }
        }
    }

    override fun onDetach() {
        listener = null

        super.onDetach()
    }



    //открываем фрагмент MovieDetailsFragment когда кликаем по записи фильма
    interface FragmentMovieListClickListener {
       fun onMovieListSelected()
    }

    companion object {
     //   fun create() = FragmentMoviesList()
    }
}