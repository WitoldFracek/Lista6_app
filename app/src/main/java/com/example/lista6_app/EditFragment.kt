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
import com.example.lista6_app.data.Animal
import com.example.lista6_app.data.AnimalViewModel
import com.example.lista6_app.data.CAT
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class EditFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var navController: NavController
    private lateinit var animalVM: AnimalViewModel

    private lateinit var nameText: TextView
    private var name = ""
    private lateinit var breedText: TextView
    private var breed = ""
    private lateinit var speciesGroup: RadioGroup
    private var species = ListAdapter.CAT
    private lateinit var image: ImageView
    private lateinit var redBar: SeekBar
    private lateinit var greenBar: SeekBar
    private lateinit var blueBar: SeekBar
    private lateinit var genderGroup: RadioGroup
    private var gender = 'M'
    private lateinit var behaviourBar: RatingBar
    private var behaviour = 0F
    private lateinit var ageText: TextView
    private lateinit var ageBar: SeekBar
    private var age = 1
    private var animal: Animal? = null

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

        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE

        navController = view.findNavController()

        if(savedInstanceState != null){
            animal = savedInstanceState.getParcelable(DataStore.ANIMAL)
        }

        nameText = view.findViewById(R.id.edit_name)
        breedText = view.findViewById(R.id.edit_breed)
        speciesGroup = view.findViewById(R.id.species_radio_group)
        image = view.findViewById(R.id.edit_image)
        redBar = view.findViewById(R.id.red_bar)
        greenBar = view.findViewById(R.id.green_bar)
        blueBar = view.findViewById(R.id.blue_bar)
        genderGroup = view.findViewById(R.id.gender_radio_group)
        behaviourBar = view.findViewById(R.id.edit_bahaviour_bar)
        behaviourBar.numStars = 5
        ageBar = view.findViewById(R.id.edit_age_bar)
        ageText = view.findViewById(R.id.age_text)

        parentFragmentManager.setFragmentResultListener(DataStore.LV_DATA_TO_EDIT, viewLifecycleOwner) { _ , bundle ->
            animal = bundle.getParcelable(DataStore.ANIMAL)
            nameText.text = if(animal == null){
                "Name"
            } else {
                animal?.name
            }

            breedText.text = if(animal == null){
                "Breed"
            } else {
                animal?.breed
            }

            if(animal == null){
                speciesGroup.check(R.id.radio_cat)
                image.setImageResource(R.drawable.cat)
                image.setBackgroundColor(Color.WHITE)
                redBar.progress = 0
                greenBar.progress = 0
                blueBar.progress = 0
                genderGroup.check(R.id.radio_male)
                behaviourBar.rating = 0F
                ageBar.progress = 1
                ageText.text = "Age: 1"
            } else {
                speciesGroup.check(
                    if(animal?.species == CAT){
                        R.id.radio_cat
                    } else {
                        R.id.radio_dog
                    }
                )

                image.setImageResource(
                    if(animal?.species == CAT){
                        R.drawable.cat
                    } else {
                        R.drawable.dog
                    }
                )
                val col = Color.rgb(animal?.red?:20, animal?.green?:20, animal?.blue?:20)
                image.setBackgroundColor(col)
                redBar.progress = Color.red(col)
                greenBar.progress = Color.green(col)
                blueBar.progress = Color.blue(col)
                genderGroup.check(
                    if(animal?.gender == 'M'){
                        R.id.radio_male
                    } else {
                        R.id.radio_female
                    }
                )
                behaviourBar.rating = animal?.behaviour?:4f
                ageBar.progress = animal?.age?:5
                ageText.text = "Age: ${animal?.age}"
            }
        }

        speciesGroup.setOnCheckedChangeListener { _ , checkedId ->
            when(checkedId) {
                R.id.radio_cat -> {
                    species = ListAdapter.CAT
                    image.setImageResource(R.drawable.cat)
                }
                R.id.radio_dog -> {
                    species = ListAdapter.DOG
                    image.setImageResource(R.drawable.dog)
                }
            }
        }

        redBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val color = Color.rgb(progress, greenBar.progress, blueBar.progress)
                image.setBackgroundColor(color)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        greenBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val color = Color.rgb(redBar.progress, progress, blueBar.progress)
                image.setBackgroundColor(color)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        blueBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val color = Color.rgb(redBar.progress, greenBar.progress, progress)
                image.setBackgroundColor(color)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        genderGroup.setOnCheckedChangeListener { _ , checkedId ->
            when(checkedId) {
                R.id.radio_male -> {
                    gender = 'M'
                }
                R.id.radio_female -> {
                    gender = 'F'
                }
            }
        }

        behaviourBar.setOnRatingBarChangeListener { _, rating, _ ->
            behaviour = rating
        }

        ageBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                age = progress
                ageText.text = "Age: $age"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        val cancelButton: Button = view.findViewById(R.id.edit_cancel_button)
        cancelButton.setOnClickListener {
            if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                navController.navigate(R.id.action_global_rightFragment)
            } else if(animal == null) {
                navController.navigate(R.id.action_global_rightFragment)
            } else {
                val myBundle = Bundle()
                myBundle.putParcelable(DataStore.ANIMAL, animal)
                parentFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_DETAILS, myBundle)
                navController.navigate(R.id.action_global_detailsFragment)
            }
        }

        val saveButton: Button = view.findViewById(R.id.edit_save_button)
        saveButton.setOnClickListener {
            name = nameText.text.toString()
            breed = breedText.text.toString()

            val modAnimal = Animal(animal?.id?:0,
                name,
                breed,
                species,
                redBar.progress,
                greenBar.progress,
                blueBar.progress,
                gender,
                behaviour,
                age
            )

            if(animal == null){
                val animalNew = Animal(0, name, breed, species, redBar.progress, greenBar.progress, blueBar.progress,
                    gender, behaviour, age)
                animalVM.addAnimal(animalNew)
            } else {
                animalVM.updateAnimal(modAnimal)
            }
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()

            if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                navController.navigate(R.id.action_global_rightFragment)
            } else if(animal == null) {
                val bundle = Bundle()
                bundle.putString(DataStore.LV_DATA_CHANGED, "changed")
                parentFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_RIGHT, bundle)
                navController.navigate(R.id.action_global_rightFragment)
            } else {
                val myBundle = Bundle()
                myBundle.putParcelable(DataStore.ANIMAL, modAnimal)
                parentFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_DETAILS, myBundle)
                navController.navigate(R.id.action_global_detailsFragment)
            }
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(DataStore.ANIMAL, animal)
        super.onSaveInstanceState(outState)
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