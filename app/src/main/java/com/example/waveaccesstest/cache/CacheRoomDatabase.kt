package com.example.waveaccesstest.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.waveaccesstest.model.cache.BooleanConverter
import com.example.waveaccesstest.model.cache.Candidate
import com.example.waveaccesstest.model.cache.FriendsConverter

@Database(
    entities = [
        Candidate::class
    ],
    version = 1
)

@TypeConverters(
    FriendsConverter::class,
    BooleanConverter::class
)
abstract class CacheRoomDatabase : RoomDatabase() {

    abstract fun CandidatesDao(): CandidatesDao

    companion object {
        const val DATABASE_NAME = "cache_db"
    }
}