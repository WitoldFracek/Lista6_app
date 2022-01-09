package com.example.lista6_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListHolderAdapter : RecyclerView.Adapter<ListHolderAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.list_elem_image)
        val nameText: TextView = view.findViewById(R.id.list_elem_main_text)
        val breedText: TextView = view.findViewById(R.id.list_elem_additional_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_elem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(ListAdapter.species[position]){
            ListAdapter.CAT -> R.drawable.cat
            else -> R.drawable.dog
        }.also { holder.image.setImageResource(it) }
        holder.image.setBackgroundColor(ListAdapter.colors[position])
        holder.nameText.text = ListAdapter.names[position]
        holder.breedText.text = ListAdapter.breeds[position]
    }

    override fun getItemCount(): Int {
        return ListAdapter.species.size
    }
}