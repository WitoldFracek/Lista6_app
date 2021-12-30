package com.example.lista6_app

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.R
import android.widget.AbsListView


class ImageAdapter(c: Context) : BaseAdapter() {

    private val ctx: Context = c

    override fun getCount(): Int {
        return DataStore.images.size
    }

    override fun getItem(position: Int): Any {
        return Any()
    }

    override fun getItemId(position: Int): Long {
        return 0
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val imageView: ImageView
        if (convertView == null) {
            imageView = ImageView(ctx)
            imageView.layoutParams = AbsListView.LayoutParams(500, 500)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(5, 5, 5, 5)
        } else {
            imageView = convertView as ImageView
        }
        imageView.setImageResource(DataStore.images[position])
        return imageView
    }
}