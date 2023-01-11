package com.example.waveaccesstest.model.data

data class Candidate(
    val id: Long,
    val isActive: Boolean,
    val age: Int,
    val eyeColor: String,
    val name: String,
    val company: String,
    val email: String,
    val phone: String,
    val address: String,
    val about: String,
    val registered: String,
    val latitude: Double,
    val longitude: Double,
    val friends: List<Long>,
    val favoriteFruit: String
)