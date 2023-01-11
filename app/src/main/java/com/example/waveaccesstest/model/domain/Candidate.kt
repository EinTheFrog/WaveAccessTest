package com.example.waveaccesstest.model.domain

import java.time.LocalDateTime

data class Candidate(
    val id: Long,
    val isActive: Boolean,
    val age: Int,
    val eyeColor: EyeColor,
    val name: String,
    val company: String,
    val email: String,
    val phone: String,
    val address: String,
    val about: String,
    val registered: LocalDateTime,
    val latitude: Double,
    val longitude: Double,
    val friends: List<Long>,
    val favoriteFruit: Fruit
)