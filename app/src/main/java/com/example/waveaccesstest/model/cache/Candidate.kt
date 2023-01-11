package com.example.waveaccesstest.model.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

@Entity(tableName = Candidate.TABLE_NAME)
data class Candidate(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "isActive")
    val isActive: Boolean,
    @ColumnInfo(name = "age")
    val age: Int,
    @ColumnInfo(name = "eyeColor")
    val eyeColor: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "company")
    val company: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "phone")
    val phone: String,
    @ColumnInfo(name = "address")
    val address: String,
    @ColumnInfo(name = "about")
    val about: String,
    @ColumnInfo(name = "registered")
    val registered: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "friends")
    val friends: List<Long>,
    @ColumnInfo(name = "favoriteFruit")
    val favoriteFruit: String

) {
    companion object {
        const val TABLE_NAME = "candidates_table"
    }
}

class FriendsConverter {

    private val gson by lazy { GsonBuilder().create() }
    private val type = object : TypeToken<List<Long>>() {}.type

    @TypeConverter
    fun fromListToString(friends: List<Long>): String {
        return gson.toJson(friends, type)
    }

    @TypeConverter
    fun fromStringToList(friends: String): List<Long> {
        return gson.fromJson(friends, type)
    }

}

class BooleanConverter {
    @TypeConverter
    fun fromBooleanToInt(value: Boolean): Int {
        return if (value) 1 else 0
    }

    @TypeConverter
    fun fromIntToBoolean(value: Int): Boolean {
        return value != 0
    }
}