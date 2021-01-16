package com.example.androidacademy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademy.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.androidacademy.data.Actor

class ActorAdapterViewholder : RecyclerView.Adapter<ActorViewHolder>() {

    private var actorsList = listOf<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder =
        ActorViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_holder_actor, parent, false))

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
            holder.onBind(actorsList[position])
            holder.itemView.setOnClickListener {
            }
    }

    override fun getItemCount(): Int = actorsList.size

    fun bindActors(newActorsList: List<Actor>) {
        actorsList = newActorsList
        notifyDataSetChanged()
    }
}

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

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
            .load(actor.picture)
            .apply(imageOption)
            .into(imgViewActors)

        txtViewActorName?.text = actor.name
    }

}


