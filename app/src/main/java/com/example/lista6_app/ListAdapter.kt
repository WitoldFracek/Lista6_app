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
        return images.size
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
        myLVItem!!.breed?.text = breed[position]
        myLVItem!!.image?.setImageResource(images[position])
        myLVItem!!.image?.setBackgroundColor(colors[position])
        return liv
    }

    companion object{
        val images = mutableListOf(
            R.drawable.cat,
            R.drawable.cat,
            R.drawable.dog)

        val names = mutableListOf(
            "Puszek",
            "Neo",
            "Burek"
        )

        val breed = mutableListOf(
            "Maine Coon",
            "British Shorthaired",
            "Golden Retriever"
        )

        val colors = mutableListOf(
            Color.rgb(194, 67, 27),
            Color.GRAY,
            Color.rgb(81, 39, 18)
        )
    }

}