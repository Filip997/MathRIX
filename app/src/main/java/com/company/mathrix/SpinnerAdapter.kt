package com.company.mathrix

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.ImageView

class SpinnerAdapter(context: Context, val avatars: Array<Int>)
    : ArrayAdapter<Int>(context, android.R.layout.simple_list_item_1, avatars) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getImageForPosition(position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getImageForPosition(position)
    }

    private fun getImageForPosition(position: Int) : View {
        val imageView = ImageView(context)
        imageView.setBackgroundResource(avatars[position])
        imageView.layoutParams = AbsListView.LayoutParams(150, 150)
        return imageView
    }
}