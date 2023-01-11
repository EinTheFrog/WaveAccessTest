package com.example.waveaccesstest.model.domain

enum class EyeColor(name: String) {
    BROWN("brown"), GREEN("green"), BLUE("blue");

    companion object {
        val DEFAULT = BROWN

        fun getColorByName(name: String): EyeColor {
            return when(name) {
                "brown" -> BROWN
                "green" -> GREEN
                "blue" -> BLUE
                else -> DEFAULT
            }
        }
    }
}