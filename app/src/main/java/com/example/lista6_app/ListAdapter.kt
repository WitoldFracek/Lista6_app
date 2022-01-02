package com.example.lista6_app

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.ImageView

class ListAdapter(ctx: Context) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(ctx)
    private var myLVItem: LVItem? = null

    private class LVItem {
        var name: TextView? = null
        var breed: TextView? = null
        var image: ImageView? = null
    }

    override fun getCount(): Int {
        return species.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, listItemView: View?, parent: ViewGroup?): View? {
        var liv = listItemView
        if(liv == null) {
            liv = inflater.inflate(R.layout.list_elem, null)
            myLVItem = LVItem()
            myLVItem!!.name = liv.findViewById(R.id.list_elem_main_text)
            myLVItem!!.breed = liv.findViewById(R.id.list_elem_additional_text)
            myLVItem!!.image = liv.findViewById(R.id.list_elem_image)
            liv.tag = myLVItem
        } else {
            myLVItem = liv.tag as LVItem
        }

        myLVItem!!.name?.text = names[position]
        myLVItem!!.breed?.text = breeds[position]
        myLVItem!!.image?.setImageResource(if(species[position] == CAT) {R.drawable.cat} else {R.drawable.dog})
        myLVItem!!.image?.setBackgroundColor(colors[position])
        return liv
    }

    companion object{
        const val CAT = 0
        const val DOG = 1

        val species = mutableListOf(
            CAT,
            CAT,
            DOG)

        val names = mutableListOf(
            "Puszek",
            "Neo",
            "Burek"
        )

        val breeds = mutableListOf(
            "Maine Coon",
            "British Shorthaired",
            "Golden Retriever"
        )

        val colors = mutableListOf(
            Color.rgb(194, 67, 27),
            Color.GRAY,
            Color.rgb(81, 39, 18)
        )

        val genders = mutableListOf(
            'F',
            'M',
            'F'
        )

        val ages = mutableListOf(
            10,
            8,
            5
        )

        val behaviours = mutableListOf(
            3.5F,
            4.5F,
            2F
        )

        private fun addToList(name: String?, breed: String?, color: Int?, spec: Int? = CAT, gender: Char? = 'M', age: Int? = 1, behaviour: Float? = 3F){
            names.add(name!!)
            breeds.add(breed!!)
            colors.add(color!!)
            genders.add(gender!!)
            ages.add(age!!)
            behaviours.add(behaviour!!)
            species.add(spec!!)
        }

        fun updateList(position: Int, name: String?, breed: String?, color: Int?, spec: Int? = CAT, gender: Char? = 'M', age: Int? = 1, behaviour: Float? = 3F){
            if(position == -1){
                addToList(name, breed, color, spec, gender, age, behaviour)
                return
            }
            names[position] = name!!
            breeds[position] = breed!!
            colors[position] = color!!
            genders[position] = gender!!
            ages[position] = age!!
            behaviours[position] = behaviour!!
            species[position] = spec!!
        }

    }

}