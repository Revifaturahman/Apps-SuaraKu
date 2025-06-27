package com.example.suaraku.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import com.example.suaraku.R
import com.example.suaraku.adapter.SpeakAdapter
import com.example.suaraku.databinding.FragmentBookmarkBinding
import com.example.suaraku.viewmodel.SpeakViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SpeakViewModel
    private lateinit var adapter: SpeakAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SpeakViewModel::class.java]
        adapter = SpeakAdapter(
            onClick = { speak ->
                // Aksi ketika item diklik → misalnya edit
                val bundle = Bundle().apply {
                    putParcelable("speak", speak)
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, HomeFragment::class.java, bundle)
                    .addToBackStack(null)
                    .commit()
            },
            onDeleteClick = { speak ->
                // Aksi ketika tombol delete diklik → tampilkan konfirmasi
                AlertDialog.Builder(requireContext())
                    .setTitle("Hapus Data")
                    .setMessage("Apakah Anda yakin ingin menghapus data ini?")
                    .setPositiveButton("Hapus") { _, _ ->
                        lifecycleScope.launch {
                            viewModel.deleteSpeak(speak)
                            Toast.makeText(requireContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .setNegativeButton("Batal", null)
                    .show()
            }
        )



        binding.rcSpeak.layoutManager = LinearLayoutManager(requireContext())
        binding.rcSpeak.adapter = adapter

        viewModel.speakList.observe(viewLifecycleOwner){list ->
//            Log.d("BookmarkFragment", "Data dari ViewModel: ${list.size}")
            adapter.submitList(list.toList())

            if (list.isNullOrEmpty()){
                binding.textEmpty.visibility = View.VISIBLE
                binding.rcSpeak.visibility = View.GONE
            }else{
                binding.textEmpty.visibility = View.GONE
                binding.rcSpeak.visibility = View.VISIBLE
            }
        }

        viewModel.loadSpeak()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}