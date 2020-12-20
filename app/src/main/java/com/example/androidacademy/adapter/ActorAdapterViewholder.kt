package com.example.androidacademy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademy.R
import com.example.androidacademy.model.Actor
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ActorAdapterViewholder(
      //  private val actorsclickListener: OnRecyclerActorsItemClickListener
        ) : RecyclerView.Adapter<ActorsViewHolder>() {


    private var actorsList = listOf<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder =
        ActorViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_holder_actor, parent, false))

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        when (holder) {
        is ActorViewHolder ->{
            holder.onBind(actorsList[position])
            holder.itemView.setOnClickListener {
//                if (actorsclickListener != null) {
//                    actorsclickListener.onClick(actorsList[position])
//                }
            }
        }
        }
    }

    override fun getItemCount(): Int = actorsList.size

    fun bindActors(newActorsList: List<Actor>) {
        actorsList = newActorsList
        notifyDataSetChanged()
    }
}

abstract class ActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

private class ActorViewHolder(itemView: View) : ActorsViewHolder(itemView){

    companion object {
        private val imageOption = RequestOptions()
            .placeholder(R.drawable.ic_combined_shape)
            .fallback(R.drawable.ic_combined_shape)
            .centerCrop()
    }

    private val imgViewActors = itemView.findViewById<ImageView>(R.id.imgViewActors)
    private val txtViewActorName = itemView.findViewById<TextView>(R.id.txtViewActorName)

    fun onBind(actor: Actor) {
        Glide.with(itemView.context)
                .load(actor.foto_actor)
                .apply(imageOption)
                .into(imgViewActors)

        imgViewActors.setImageResource(actor.foto_actor)
        txtViewActorName?.text = actor.name
    }

}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context


interface OnRecyclerActorsItemClickListener {
    fun onClick(actor: Actor)
}