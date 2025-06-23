package com.example.suaraku.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.suaraku.data.model.Speak

@Dao
interface SpeakDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(speak: Speak)

    @Update
    suspend fun update(speak: Speak)

    @Delete
    suspend fun delete(speak: Speak)

    @Query("SELECT * FROM speak")
    suspend fun getAll(): List<Speak>
}