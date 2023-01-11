package com.example.waveaccesstest.di

import android.content.Context
import androidx.room.Room
import com.example.waveaccesstest.cache.CacheRoomDatabase
import com.example.waveaccesstest.cache.CandidatesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideCacheDB(@ApplicationContext context: Context): CacheRoomDatabase {
        return Room.databaseBuilder(
            context,
            CacheRoomDatabase::class.java,
            CacheRoomDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideGroupsDao(cacheRoomDatabase: CacheRoomDatabase): CandidatesDao {
        return cacheRoomDatabase.CandidatesDao()
    }
}