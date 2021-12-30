package com.example.lista6_app

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var navController: NavController

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
        return if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            inflater.inflate(R.layout.fragment_home, container, false)
        } else {
            inflater.inflate(R.layout.fragment_home_landscape, container, false)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        val invitationText: TextView = view.findViewById(R.id.invitation)
        //val invitationTextLandscape: TextView = view.findViewById(R.id.invitation_landscape)

        val btmNav: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
        btmNav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.btm_left -> {
                    val myBundle = Bundle()
                    myBundle.putString(DataStore.INVITATION_KEY, invitationText.text.toString())
                    parentFragmentManager.setFragmentResult(DataStore.INVITATION_KEY, myBundle)
                    navController.navigate(R.id.action_global_leftFragment)
                }
                R.id.btm_center -> navController.navigate(R.id.action_global_homeFragment)
                R.id.btm_right -> {}
            }
            true
        }

        parentFragmentManager.setFragmentResultListener(DataStore.INVITATION_KEY, viewLifecycleOwner) {key, bundle ->
            val newInv = bundle.getString(DataStore.INVITATION_KEY, "Hello")
            invitationText.text = newInv
            //invitationTextLandscape.text = newInv
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}