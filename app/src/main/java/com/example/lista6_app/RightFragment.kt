package com.example.lista6_app

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RightFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_right, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                View.VISIBLE
            } else {
                View.GONE
            }

        val listView: ListView = view.findViewById(R.id.list_view)
        val adapter = ListAdapter(view.context)
        listView.adapter = adapter

        navController = view.findNavController()

        listView.onItemClickListener = object: AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(view == null){
                    return
                }
                val myBundle = Bundle()
                with(myBundle) {
                    putInt(DataStore.LV_POSITION, position)
                }

                if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                    parentFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_DETAILS, myBundle)
                    requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE
                    MainActivity.backToRight = true
                    navController.navigate(R.id.action_global_detailsFragment)
                } else {
                    childFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_DETAILS, myBundle)
                }
            }
        }

        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val fab: FloatingActionButton = view.findViewById(R.id.add_to_list_fab)
            fab.setOnClickListener{
                val myBundle = Bundle()
                myBundle.putInt(DataStore.LV_POSITION, -1)
                parentFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_EDIT, myBundle)
                navController.navigate(R.id.action_global_editFragment)

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RightFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}