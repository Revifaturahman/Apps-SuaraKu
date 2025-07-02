package com.example.suaraku.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.suaraku.data.model.Speak
import com.example.suaraku.databinding.ItemSpeakBinding

class SpeakAdapter(private val onClick: (Speak) -> Unit, private val onDeleteClick: (Speak) -> Unit
): ListAdapter<Speak, SpeakAdapter.ViewHolder>(DiffCallBack()) {

    class ViewHolder(private val binding: ItemSpeakBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(speak: Speak, onClick: (Speak) -> Unit, onDeleteClick: (Speak) -> Unit){
            binding.textValue.text = "Teks: ${speak.text}"
            binding.genderValue.text = "Jenis Kelamin: ${speak.gender}"
            binding.pitchValue.text = "Nada: ${speak.pitch}"

//            Ubah warna delete
            binding.deleteSpeak.setColorFilter(
                androidx.core.content.ContextCompat.getColor(binding.root.context, android.R.color.holo_red_light)
            )

            binding.root.setOnClickListener {
                onClick(speak)
            }

            binding.deleteSpeak.setOnClickListener {
                onDeleteClick(speak)
            }
        }
    }

    class DiffCallBack: DiffUtil.ItemCallback<Speak>(){
        override fun areItemsTheSame(oldItem: Speak, newItem: Speak): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Speak, newItem: Speak): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSpeakBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val speak = getItem(position)
//        Log.d("SpeakAdapter", "Item ke-$position: ${speak.text}")
        holder.bind(speak, onClick, onDeleteClick)
    }
}