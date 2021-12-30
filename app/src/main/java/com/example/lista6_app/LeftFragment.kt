package com.example.lista6_app

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class LeftFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_left, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lateinit var invitation: String

        val sharedPrefs = requireActivity().getSharedPreferences(DataStore.SHARED_PREFS, Context.MODE_PRIVATE)
        val index = sharedPrefs.getInt(DataStore.IMAGE_INDEX_KEY, 0)

        parentFragmentManager.setFragmentResultListener(DataStore.DATA_TO_LEFT, viewLifecycleOwner) { key, bundle ->
            invitation = bundle.getString(DataStore.INVITATION_KEY, "Hello")
        }

        val invitationList: Spinner = view.findViewById(R.id.invitation_list)
        ArrayAdapter.createFromResource(
            requireContext().applicationContext,
            R.array.invitations,
            R.layout.spiner_item
        ).also{ adapter -> invitationList.adapter = adapter}

        invitationList.setSelection(sharedPrefs.getInt(DataStore.INVITATION_POSITION, 0))


        invitationList.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val invs = resources.getStringArray(R.array.invitations)
                val newInv = invs[position]
                val myBundle = Bundle()
                myBundle.putString(DataStore.INVITATION_KEY, newInv)
                parentFragmentManager.setFragmentResult(DataStore.INVITATION_KEY_BACK, myBundle)
                val editor = sharedPrefs.edit()
                editor.putString(DataStore.INVITATION_KEY, newInv)
                editor.putInt(DataStore.INVITATION_POSITION, position)
                editor.apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        val imageGrid: GridView = view.findViewById(R.id.image_grid)
        val adapter = ImageAdapter(requireContext())
        imageGrid.adapter = adapter
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            imageGrid.numColumns = 3
        } else {
            imageGrid.numColumns = 2
        }

        imageGrid.onItemClickListener = object: AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val editor = sharedPrefs.edit()
                editor.putInt(DataStore.IMAGE_INDEX_KEY, position)
                editor.apply()
            }

        }


        parentFragmentManager.setFragmentResultListener(DataStore.DATA_TO_LEFT, viewLifecycleOwner) {key, bundle ->
            val index = bundle.getInt(DataStore.IMAGE_INDEX_KEY, 0)
        }


    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LeftFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}