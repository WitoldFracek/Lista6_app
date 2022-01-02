package com.example.lista6_app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DetailsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var nameText: TextView
    private lateinit var breedText: TextView
    private lateinit var speciesText: TextView
    private lateinit var genderText: TextView
    private lateinit var image: ImageView
    private lateinit var behaviourBar: RatingBar
    private lateinit var ageText: TextView
    private var position = -1

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameText = view.findViewById(R.id.details_name)
        breedText = view.findViewById(R.id.details_breed)
        speciesText = view.findViewById(R.id.details_species)
        ageText = view.findViewById(R.id.details_age)
        genderText = view.findViewById(R.id.details_gender)
        image = view.findViewById(R.id.details_image)
        behaviourBar = view.findViewById(R.id.details_behaviour_bar)
        behaviourBar.isEnabled = false

        parentFragmentManager.setFragmentResultListener(DataStore.LV_DATA_TO_DETAILS, viewLifecycleOwner) { _, bundle ->
            position = bundle.getInt(DataStore.LV_POSITION, 0)

            nameText.text = "Name: ${ListAdapter.names[position]}"
            breedText.text = "Breed: ${ListAdapter.breeds[position]}"
            genderText.text = if(ListAdapter.genders[position] == 'M') {
                "Male"
            } else {
                "Female"
            }
            if(ListAdapter.species[position] == ListAdapter.CAT){
                speciesText.text = "Cat"
                image.setImageResource(R.drawable.cat)
            } else {
                speciesText.text = "Dog"
                image.setImageResource(R.drawable.dog)
            }
            image.setBackgroundColor(ListAdapter.colors[position])
            ageText.text = "Age: ${ListAdapter.ages[position]}"
            behaviourBar.rating = ListAdapter.behaviours[position]
        }

        navController = view.findNavController()

        val backButton: Button = view.findViewById(R.id.details_back_button)
        backButton.setOnClickListener {
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.VISIBLE
            MainActivity.backToRight = false
            navController.navigate(R.id.action_global_rightFragment)
        }


    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}