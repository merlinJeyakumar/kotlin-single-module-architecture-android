package com.nativedevps.myapplication.di

import android.content.Context
import com.nativedevps.myapplication.data.local.room.RoomManager
import com.nativedevps.myapplication.data.local.room.dao.SampleItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideSampleDao(roomManager: RoomManager): SampleItemDao {
        return roomManager.sampleItemDao
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): RoomManager {
        return RoomManager.getInstance(appContext)
    }
}