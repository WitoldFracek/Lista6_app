package com.example.lista6_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.navigation.NavController
import androidx.navigation.findNavController

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
                with(myBundle){
                    putInt(DataStore.LV_IMAGE, ListAdapter.images[position])
                    putString(DataStore.LV_NAME, ListAdapter.names[position])
                    putString(DataStore.LV_BREED, ListAdapter.breeds[position])
                    putChar(DataStore.LV_GENDER, ListAdapter.genders[position])
                    putInt(DataStore.LV_COLOR, ListAdapter.colors[position])
                    putInt(DataStore.LV_AGE, ListAdapter.ages[position])
                    putFloat(DataStore.LV_BEHAVIOUR, ListAdapter.behaviours[position])
                }
                parentFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_DETAILS, myBundle)
                navController.navigate(R.id.action_global_detailsFragment)
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