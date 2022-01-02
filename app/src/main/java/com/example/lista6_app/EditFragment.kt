package com.example.lista6_app

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class EditFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

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
    private var position = -1

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
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.backToRight = true

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE

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
            position = bundle.getInt(DataStore.LV_POSITION, -1)
            //Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
            nameText.text = if(position == -1){
                "Name"
            } else {
                ListAdapter.names[position]
            }

            breedText.text = if(position == -1){
                "Breed"
            } else {
                ListAdapter.breeds[position]
            }

            if(position == -1){
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
                    if(ListAdapter.species[position] == ListAdapter.CAT){
                        R.id.radio_cat
                    } else {
                        R.id.radio_dog
                    }
                )

                image.setImageResource(
                    if(ListAdapter.species[position] == ListAdapter.CAT){
                        R.drawable.cat
                    } else {
                        R.drawable.dog
                    }
                )
                image.setBackgroundColor(ListAdapter.colors[position])
                redBar.progress = Color.red(ListAdapter.colors[position])
                greenBar.progress = Color.green(ListAdapter.colors[position])
                blueBar.progress = Color.blue(ListAdapter.colors[position])
                genderGroup.check(
                    if(ListAdapter.genders[position] == 'M'){
                        R.id.radio_male
                    } else {
                        R.id.radio_female
                    }
                )
                behaviourBar.rating = ListAdapter.behaviours[position]
                ageBar.progress = ListAdapter.ages[position]
                ageText.text = "Age: ${ListAdapter.ages[position]}"
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


    }

    override fun onDestroy() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.VISIBLE
        super.onDestroy()
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