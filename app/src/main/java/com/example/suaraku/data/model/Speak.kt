package com.example.suaraku.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity("speak")
@Parcelize
data class Speak(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    val gender: String,
    val pitch: String
): Parcelable
