package com.revifaturahman.suaraku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revifaturahman.suaraku.data.model.Speak
import com.revifaturahman.suaraku.repository.SpeakRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpeakViewModel @Inject constructor(
    val repository: SpeakRepository
): ViewModel() {
    private val _speakList = MutableLiveData<List<Speak>>()
    val speakList : LiveData<List<Speak>> = _speakList

    init {
        loadSpeak()
    }

    fun loadSpeak(){
        viewModelScope.launch {
            _speakList.value = repository.getAllSpeak()
        }
    }

    fun insertSpeak(speak: Speak){
        viewModelScope.launch {
            repository.insertSpeak(speak)
        }
    }

    fun updateSpeak(speak: Speak){
        viewModelScope.launch {
            repository.updateSpeak(speak)
        }
    }

    fun deleteSpeak(speak: Speak){
        viewModelScope.launch {
            repository.deleteSpeak(speak)
        }
    }
}