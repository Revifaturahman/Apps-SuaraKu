package com.example.suaraku.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.suaraku.data.dao.SpeakDao
import com.example.suaraku.data.model.Speak

@Database(
    entities =[Speak::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun SpeakDao() : SpeakDao
}