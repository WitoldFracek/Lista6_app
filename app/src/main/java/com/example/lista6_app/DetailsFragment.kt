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
import com.example.lista6_app.data.Animal
import com.example.lista6_app.data.AnimalViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.lista6_app.data.CAT
import com.example.lista6_app.data.DOG

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
    private var animal: Animal? = null

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

        if(savedInstanceState != null) {
            animal = savedInstanceState.getParcelable(DataStore.ANIMAL)
        }

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE

        nameText = view.findViewById(R.id.details_name)

        breedText = view.findViewById(R.id.details_breed)

        speciesText = view.findViewById(R.id.details_species)

        ageText = view.findViewById(R.id.details_age)

        genderText = view.findViewById(R.id.details_gender)

        image = view.findViewById(R.id.details_image)

        behaviourBar = view.findViewById(R.id.details_behaviour_bar)
        behaviourBar.isEnabled = false

        parentFragmentManager.setFragmentResultListener(DataStore.LV_DATA_TO_DETAILS, viewLifecycleOwner) { _, bundle ->
            animal = bundle.getParcelable(DataStore.ANIMAL)
            modify()
        }
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            childFragmentManager.setFragmentResultListener(DataStore.LV_DATA_TO_DETAILS, viewLifecycleOwner) { _ , bundle ->
                animal = bundle.getParcelable(DataStore.ANIMAL)
                modify()
            }
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
                val bundle = Bundle()
                bundle.putParcelable(DataStore.ANIMAL, animal)
                parentFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_EDIT, bundle)
                navController.navigate(R.id.action_global_editFragment)
            }

            val deleteButton: Button = view.findViewById(R.id.details_delete_button)
            deleteButton.setOnClickListener {
                navController = view.findNavController()
                if(animal != null){
                    animalVM.deleteAnimal(animal as Animal)
                    Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
                }
                navController.navigate(R.id.action_global_rightFragment)
            }
        }
    }

    private fun modify() {
        if(animal == null){
            animal = animalVM.getFirst()
        }
        nameText.text = animal?.name
        breedText.text = animal?.breed

        speciesText.text = if(animal?.species == CAT) {
            "Cat"
        } else {
            "Dog"
        }

        ageText.text = "Age: ${animal?.age}"

        genderText.text = if(animal?.gender == 'M'){
            "Male"
        } else {
            "Female"
        }

        image.setImageResource(if(animal?.species == CAT){
            R.drawable.cat
        } else {
            R.drawable.dog
        })
        image.setBackgroundColor(Color.rgb(animal?.red ?: 20, animal?.green ?: 20, animal?.blue ?: 20))

        behaviourBar.rating = animal?.behaviour?:4f
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