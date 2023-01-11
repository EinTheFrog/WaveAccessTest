package com.example.waveaccesstest.model.domain

data class Candidate(
    val id: Int,
    val isActive: Boolean,
    val age: Int,
    val eyeColor: EyeColor,
    val name: String,
    val company: String,
    val email: String,
    val phone: String,
    val address: String,
    val about: String,
    val registered: String,
    val latitude: Double,
    val longitude: Double,
    val friends: List<Int>,
    val favoriteFruit: Fruit
)