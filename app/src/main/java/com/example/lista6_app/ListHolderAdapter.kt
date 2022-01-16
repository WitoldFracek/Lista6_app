package com.example.lista6_app

import android.content.ClipData
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.findFragment
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.lista6_app.data.Animal
import com.example.lista6_app.data.CAT
import com.example.lista6_app.data.DOG

class ListHolderAdapter : RecyclerView.Adapter<ListHolderAdapter.ViewHolder>(){

    private var animalsList = emptyList<Animal>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.list_elem_image)
        val nameText: TextView = view.findViewById(R.id.list_elem_main_text)
        val breedText: TextView = view.findViewById(R.id.list_elem_additional_text)

//        init {
//            view.setOnClickListener {
//
//                val action = DetailsFragmentDirections.actionGlobalDetailsFragment(currentAnimal = animalsL)
//
//                val navController = view.findNavController()
//                val rootFragment: RightFragment = it.findFragment()
//                val pos = adapterPosition
//                val bundle = Bundle()
//                bundle.putInt(DataStore.LV_POSITION, pos)
//                if(rootFragment.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
//                    navController.navigate(R.id.action_global_detailsFragment)
//                    rootFragment.parentFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_DETAILS, bundle)
//                } else {
//                    rootFragment.childFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_DETAILS, bundle)
//                }
//            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_elem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animal = animalsList[position]

        when(animal.species) {
            CAT -> R.drawable.cat
            else -> R.drawable.dog
        }.also { holder.image.setImageResource(it) }
        holder.image.setBackgroundColor(Color.rgb(animal.red, animal.green, animal.blue))
        holder.nameText.text = animal.name
        holder.breedText.text = animal.breed

        holder.itemView.setOnClickListener {
            val action = DetailsFragmentDirections.actionGlobalDetailsFragment(animal)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return animalsList.size
    }

    fun setData(animals: List<Animal>) {
        animalsList = animals
        notifyDataSetChanged()
    }

}