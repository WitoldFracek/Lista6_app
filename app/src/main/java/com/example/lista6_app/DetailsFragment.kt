package com.example.lista6_app

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lista6_app.data.AnimalViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.lista6_app.data.CAT
import com.example.lista6_app.data.DOG

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DetailsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private val args by navArgs<DetailsFragmentArgs>()

    private lateinit var nameText: TextView
    private lateinit var breedText: TextView
    private lateinit var speciesText: TextView
    private lateinit var genderText: TextView
    private lateinit var image: ImageView
    private lateinit var behaviourBar: RatingBar
    private lateinit var ageText: TextView
    private var position = 0

    private lateinit var navController: NavController
    private lateinit var animalVM: AnimalViewModel

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

        animalVM = ViewModelProvider(this).get(AnimalViewModel::class.java)

        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE

        nameText = view.findViewById(R.id.details_name)
        nameText.text = args.currentAnimal.name

        breedText = view.findViewById(R.id.details_breed)
        breedText.text = args.currentAnimal.breed

        speciesText = view.findViewById(R.id.details_species)
        speciesText.text = if(args.currentAnimal.species == CAT) {
            "Cat"
        } else {
            "Dog"
        }

        ageText = view.findViewById(R.id.details_age)
        ageText.text = "Age: ${args.currentAnimal.age}"

        genderText = view.findViewById(R.id.details_gender)
        genderText.text = if(args.currentAnimal.gender == 'M'){
            "Male"
        } else {
            "Female"
        }

        image = view.findViewById(R.id.details_image)
        image.setImageResource(if(args.currentAnimal.species == CAT){
            R.drawable.cat
        } else {
            R.drawable.dog
        })
        image.setBackgroundColor(Color.rgb(args.currentAnimal.red, args.currentAnimal.green, args.currentAnimal.blue))
        behaviourBar = view.findViewById(R.id.details_behaviour_bar)
        behaviourBar.isEnabled = false
        if(savedInstanceState != null) {
            position = savedInstanceState.getInt(DataStore.LV_POSITION, 0)
        }

        parentFragmentManager.setFragmentResultListener(DataStore.LV_DATA_TO_DETAILS, viewLifecycleOwner) { _, bundle ->
            position = bundle.getInt(DataStore.LV_POSITION, 0)
            modify()
        }
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            childFragmentManager.setFragmentResultListener(DataStore.LV_DATA_TO_DETAILS, viewLifecycleOwner) { _ , bundle ->
                position = bundle.getInt(DataStore.LV_POSITION, 0)
            }
            modify()
        }
        modify()



        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){

            val backButton: Button = view.findViewById(R.id.details_back_button)
            backButton.setOnClickListener {
                MainActivity.backToRight = false
                navController = view.findNavController()
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.VISIBLE
                navController.navigate(R.id.action_global_rightFragment)
            }

            val modifyButton: Button = view.findViewById(R.id.details_modify_button)
            modifyButton.setOnClickListener {
                navController = view.findNavController()
                val myBundle = Bundle()
                myBundle.putInt(DataStore.LV_POSITION, position)
                parentFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_EDIT, myBundle)
                navController.navigate(R.id.action_global_editFragment)
            }
        }



    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(DataStore.LV_POSITION, position)
        super.onSaveInstanceState(outState)
    }

    private fun modify(){
        val animalVM: AnimalViewModel = ViewModelProvider(this).get(AnimalViewModel::class.java)
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