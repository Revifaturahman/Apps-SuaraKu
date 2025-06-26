package com.example.suaraku.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.suaraku.R
import com.example.suaraku.data.model.SpinnerGenderModel
import com.example.suaraku.databinding.SpinnerItemGenderBinding

class SpinnerGenderAdapter(
    private val context: Context,
    val items: List<SpinnerGenderModel>
): BaseAdapter() {

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return  position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = SpinnerItemGenderBinding.inflate(LayoutInflater.from(context), parent, false)

        val item = items[position]
        binding.image.setImageResource(item.icon)
        binding.text.text = item.label

        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = SpinnerItemGenderBinding.inflate(LayoutInflater.from(context), parent, false)

        val item = items[position]
        binding.image.setImageResource(item.icon)
        binding.text.text = item.label

        return binding.root
    }
}