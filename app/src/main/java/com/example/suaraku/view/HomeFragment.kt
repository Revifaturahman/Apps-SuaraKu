package com.example.suaraku.view

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.suaraku.R
import com.example.suaraku.adapter.SpinnerGenderAdapter
import com.example.suaraku.data.model.Speak
import com.example.suaraku.data.model.SpinnerGenderModel
import com.example.suaraku.databinding.FragmentHomeBinding
import com.example.suaraku.viewmodel.SpeakViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint

class HomeFragment : Fragment(), TextToSpeech.OnInitListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

//    View Binding
    private val viewModel: SpeakViewModel by viewModels()

    private var speak: Speak? = null

    private var isTTSReady = false

    //TTS
    private lateinit var tts: TextToSpeech
    private var availableVoice: List<Voice> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // binding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

//        Inisialisasi Text To Speach
        tts =TextToSpeech(requireContext(), this)

//        Spinner Gender
        val spinnerGenderList = listOf(
            SpinnerGenderModel("Laki-laki", R.drawable.ic_male),
            SpinnerGenderModel("Perempuan", R.drawable.ic_female)
        )
        val adapterGender = SpinnerGenderAdapter(requireContext(), spinnerGenderList)
        binding.spinnerGender.adapter = adapterGender

//        Spinner Nada
        val spinnerPitchList = listOf("Tinggi", "Normal", "Rendah")
        val adapterPitch = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item_pitch,
            spinnerPitchList
        ).also {
            it.setDropDownViewResource(R.layout.spinner_item_pitch)
        }
        binding.spinnerPitch.adapter = adapterPitch
        binding.spinnerPitch.setSelection(1)

        binding.spinnerGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long) {
                val selected = binding.spinnerGender.selectedItem as SpinnerGenderModel

                if (isTTSReady && availableVoice.isNotEmpty()) {
                    val selectedVoice = when (selected.label) {
                        "Laki-laki" -> availableVoice.find { it.name == "id-id-x-ide-local" }
                        "Perempuan" -> availableVoice.find { it.name == "id-id-x-dfz-local" }
                        else -> null
                    }

                    if (selectedVoice != null) {
                        tts.voice = selectedVoice
                        Toast.makeText(requireContext(), "Suara: ${selected.label}", Toast.LENGTH_SHORT).show()
//                        Log.d("VOICE_SELECTED", "Voice dipilih: ${selectedVoice.name}")
                    } else {
                        Toast.makeText(requireContext(), "Suara Tidak Ditemukan", Toast.LENGTH_SHORT).show()
                    }
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.playSoundContainer.setOnClickListener {
            speakOut()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        speak = arguments?.getParcelable("speak")
        speak?.let {it ->
            binding.edtText.setText(it.text)

            val genderIndex = (binding.spinnerGender.adapter as SpinnerGenderAdapter).items
                .indexOfFirst { genderModel -> genderModel.label == it.gender }

            if (genderIndex != -1) {
                binding.spinnerGender.setSelection(genderIndex)
            }


            val pitchIndex = when(it.pitch){
                "Tinggi" -> 0
                "Normal" -> 1
                "Rendah" -> 2
                else -> 1
            }
            binding.spinnerPitch.setSelection(pitchIndex)
        }

        binding.btnBookmark.setOnClickListener{
                // Ambil data input user
                val text = binding.edtText.text?.toString().orEmpty()
                val gender = when (binding.spinnerGender.selectedItemPosition) {
                    0 -> "Laki-laki"
                    1 -> "Perempuan"
                    else -> "Laki-laki"
                }
                val pitch = when (binding.spinnerPitch.selectedItemPosition) {
                    0 -> "Tinggi"
                    1 -> "Normal"
                    2 -> "Rendah"
                    else -> "Normal"
                }
                lifecycleScope.launch {
                    if (speak == null) {
                        val speakNew = Speak(
                            text = text,
                            gender = gender,
                            pitch = pitch
                        )
                        viewModel.insertSpeak(speakNew)
                        Toast.makeText(requireContext(), "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
                    } else {
                        // Update data lama
                        val currentSpeak = speak
                        if (currentSpeak != null) {
                            val speakNew = Speak(
                                id = currentSpeak.id,
                                text = text,
                                gender = gender,
                                pitch = pitch
                            )
                            viewModel.updateSpeak(speakNew)
                            Toast.makeText(requireContext(), "Data Berhasil Diupdate", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale("id", "ID")

            // Filter hanya yang locale in_ID
            availableVoice = tts.voices.filter { it.locale.toString() == "in_ID" }

            isTTSReady = true
            binding.playSoundContainer.isEnabled = true

            //Atur voice default (boleh pilih salah satu dari availableVoice)
            val defaultVoice = availableVoice.find { it.name == "id-id-x-idd-network" }
            if (defaultVoice != null) {
                tts.voice = defaultVoice
            }

            // Trigger ulang gender spinner
            binding.spinnerGender.setSelection(binding.spinnerGender.selectedItemPosition)
        } else {
            binding.playSoundContainer.isEnabled = false
            Toast.makeText(requireContext(), "TTS Gagal diinisialisasi", Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakOut() {
        val text = binding.edtText.text.toString()
        val selectedPitch = binding.spinnerPitch.selectedItem.toString()
        val pitchValue = when (selectedPitch) {
            "Tinggi" -> 1.5f
            "Normal" -> 1.0f
            "Rendah" -> 0.8f
            else -> 1.0f
        }

        tts.setPitch(pitchValue)

        // 🆕 Tambahkan pemilihan suara berdasarkan spinner saat ini
        val selectedGender = (binding.spinnerGender.selectedItem as SpinnerGenderModel).label
        val selectedVoice = when (selectedGender) {
            "Laki-laki" -> availableVoice.find { it.name == "id-id-x-ide-local" }
            "Perempuan" -> availableVoice.find { it.name == "id-id-x-idc-local" }
            else -> null
        }

        if (selectedVoice != null) {
            tts.voice = selectedVoice
//            Log.d("VOICE_SPEAK", "Voice dipilih ulang sebelum speak(): ${selectedVoice.name}")
        } else {
//            Log.d("VOICE_SPEAK", "Voice tidak ditemukan berdasarkan gender: $selectedGender")
        }

        if (text.isNotEmpty()) {
            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }, 300)
        } else {
            Toast.makeText(requireContext(), "Harap isi teks terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        if (::tts.isInitialized){
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }


}