package com.example.suaraku.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.example.suaraku.R
import com.example.suaraku.adapter.SpinnerGenderAdapter
import com.example.suaraku.data.model.SpinnerGenderModel
import com.example.suaraku.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // binding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

//        Spinner Gender
        val spinnerGenderList = listOf(
            SpinnerGenderModel("Laki-laki", R.drawable.ic_male),
            SpinnerGenderModel("Perempuan", R.drawable.ic_female)
        )
        val adapterGender = SpinnerGenderAdapter(requireContext(), spinnerGenderList)
        binding.spinnerGender.adapter = adapterGender

//        Spinner Nada
        val spinnerPitchList = listOf("Tinggi", "Sedang", "Rendah")
        val adapterPitch = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item_pitch,
            spinnerPitchList
        ).also {
            it.setDropDownViewResource(R.layout.spinner_item_pitch)
        }
        binding.spinnerPitch.adapter = adapterPitch

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}