package com.example.lista6_app

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class ListHolderAdapter : RecyclerView.Adapter<ListHolderAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.list_elem_image)
        val nameText: TextView = view.findViewById(R.id.list_elem_main_text)
        val breedText: TextView = view.findViewById(R.id.list_elem_additional_text)

        init {
            view.setOnClickListener {
                val navController = view.findNavController()
                val rootFragment: RightFragment = it.findFragment()
                val pos = adapterPosition
                val bundle = Bundle()
                bundle.putInt(DataStore.LV_POSITION, pos)
                if(rootFragment.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                    navController.navigate(R.id.action_global_detailsFragment)
                    rootFragment.parentFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_DETAILS, bundle)
                } else {
                    rootFragment.childFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_DETAILS, bundle)
                }
            }

            view.setOnLongClickListener {
                var ret = true
                val navController = view.findNavController()
                val rootFragment: RightFragment = it.findFragment()
                if(rootFragment.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                    ret = false
                }
                val pos = adapterPosition
                val bundle = Bundle()
                bundle.putInt(DataStore.LV_POSITION, pos)
                rootFragment.parentFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_EDIT, bundle)
                navController.navigate(R.id.action_global_editFragment)
                ret
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_elem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(ListAdapter.species[position]) {
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