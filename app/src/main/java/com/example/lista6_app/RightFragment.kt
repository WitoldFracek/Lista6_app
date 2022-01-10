package com.example.lista6_app

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RightFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerView.Adapter<ListHolderAdapter.ViewHolder>

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
        val view = inflater.inflate(R.layout.fragment_right, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        adapter = ListHolderAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                View.VISIBLE
            } else {
                View.GONE
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                View.VISIBLE
            } else {
                View.GONE
            }

//        val listView: ListView = view.findViewById(R.id.list_view)
//        val adapter = ListAdapter(view.context)
//        listView.adapter = adapter

        navController = view.findNavController()

        parentFragmentManager.setFragmentResultListener(DataStore.LV_DATA_TO_RIGHT, viewLifecycleOwner) { _, bundle ->
            val changed = bundle.getString(DataStore.LV_DATA_CHANGED, "err")
            if(changed == "changed"){
                adapter.notifyDataSetChanged()
            }
        }

//        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
//        val adapter = ListHolderAdapter()
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        recyclerView.adapter = adapter

//        listView.onItemClickListener = object: AdapterView.OnItemClickListener {
//            override fun onItemClick(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                if(view == null){
//                    return
//                }
//                val myBundle = Bundle()
//                with(myBundle) {
//                    putInt(DataStore.LV_POSITION, position)
//                }
//
//                if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
//                    MainActivity.backToRight = true
//                    parentFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_DETAILS, myBundle)
//                    navController.navigate(R.id.action_global_detailsFragment)
//                } else {
//                    childFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_DETAILS, myBundle)
//                }
//            }
//        }
//
//        listView.onItemLongClickListener = object: AdapterView.OnItemLongClickListener {
//            override fun onItemLongClick(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ): Boolean {
//                MainActivity.backToRight = true
//                if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
//                    return false
//                }
//                val myBundle = Bundle()
//                myBundle.putInt(DataStore.LV_POSITION, position)
//                parentFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_EDIT, myBundle)
//                navController.navigate(R.id.action_global_editFragment)
//                return true
//            }
//
//        }

        val fab: FloatingActionButton = view.findViewById(R.id.add_to_list_fab)
        fab.setOnClickListener{
            MainActivity.backToRight = true
            val myBundle = Bundle()
            myBundle.putInt(DataStore.LV_POSITION, -1)
            parentFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_EDIT, myBundle)
            navController.navigate(R.id.action_global_editFragment)
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