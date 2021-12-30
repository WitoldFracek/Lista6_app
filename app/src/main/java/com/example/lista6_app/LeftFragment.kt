package com.example.lista6_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
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

        val invitationList: Spinner = view.findViewById(R.id.invitation_list)
//        val invitations = arrayOf(
//            resources.getString(R.string.inv1),
//            resources.getString(R.string.inv2),
//            resources.getString(R.string.inv3),
//            resources.getString(R.string.inv4),
//            resources.getString(R.string.inv5)
//        )
        ArrayAdapter.createFromResource(
            requireContext().applicationContext,
            R.array.invitations,
            R.layout.support_simple_spinner_dropdown_item
        ).also{ adapter -> invitationList.adapter = adapter}


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
                //Toast.makeText(requireContext(), newInv, Toast.LENGTH_LONG).show()
                parentFragmentManager.setFragmentResult(DataStore.INVITATION_KEY_BACK, myBundle)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
//        parentFragmentManager.setFragmentResultListener(DataStore.INVITATION_KEY, viewLifecycleOwner) {key, bundle ->
//            val inv = bundle.getString(DataStore.INVITATION_KEY, "Hello ERR")
//
//            invitationList.prompt = if(inv.isNullOrEmpty()){"Hello"} else {inv.toString()}
//            //Toast.makeText(requireContext(), inv, Toast.LENGTH_LONG).show()
//        }


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