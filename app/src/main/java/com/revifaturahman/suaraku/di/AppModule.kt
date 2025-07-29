package com.revifaturahman.suaraku.di

import android.content.Context
import androidx.room.Room
import com.revifaturahman.suaraku.data.dao.SpeakDao
import com.revifaturahman.suaraku.data.db.AppDatabase
import com.revifaturahman.suaraku.repository.SpeakRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase{
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "suaraku.db"
        ).build()
    }

    @Provides
    fun ProvideSpeakDao(db: AppDatabase): SpeakDao = db.SpeakDao()

    fun provideRepository(
        speakDao: SpeakDao
    ): SpeakRepository {
        return SpeakRepository(speakDao)
    }
}