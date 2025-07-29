package com.revifaturahman.suaraku.repository

import com.revifaturahman.suaraku.data.dao.SpeakDao
import com.revifaturahman.suaraku.data.model.Speak
import javax.inject.Inject

class SpeakRepository @Inject constructor(
    private val SpeakDao : SpeakDao
) {
    suspend fun insertSpeak(speak: Speak) = SpeakDao.insert(speak)
    suspend fun updateSpeak(speak: Speak) = SpeakDao.update(speak)
    suspend fun deleteSpeak(speak: Speak) = SpeakDao.delete(speak)
    suspend fun getAllSpeak(): List<Speak> = SpeakDao.getAll()
}