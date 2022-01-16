package com.example.lista6_app

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lista6_app.data.AnimalViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RightFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var animalVM: AnimalViewModel

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
        val adapter = ListHolderAdapter()
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        animalVM = ViewModelProvider(this).get(AnimalViewModel::class.java)


        animalVM.readAllData.observe(viewLifecycleOwner, Observer { animals ->
            adapter.setData(animals)
        })

        registerForContextMenu(view)

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

        navController = view.findNavController()

        parentFragmentManager.setFragmentResultListener(DataStore.LV_DATA_TO_RIGHT, viewLifecycleOwner) { _, bundle ->
            val changed = bundle.getString(DataStore.LV_DATA_CHANGED, "err")
        }

        val fab: FloatingActionButton = view.findViewById(R.id.add_to_list_fab)
        fab.setOnClickListener{
            MainActivity.backToRight = true
            val myBundle = Bundle()
            myBundle.putInt(DataStore.LV_POSITION, -1)
            parentFragmentManager.setFragmentResult(DataStore.LV_DATA_TO_EDIT, myBundle)
            navController.navigate(R.id.action_global_editFragment)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val menuInflater = requireActivity().menuInflater
        menuInflater.inflate(R.menu.database_options_context_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        Toast.makeText(requireContext(), "DELETE", Toast.LENGTH_SHORT).show()
        when(item.itemId) {
            R.id.db_delete -> Toast.makeText(requireContext(), "DELETE", Toast.LENGTH_SHORT).show()
        }
        return super.onContextItemSelected(item)
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