package com.example.waveaccesstest.model.domain

enum class Fruit(name: String) {
    APPLE("apple"), BANANA("banana"), STRAWBERRY("strawberry");

    companion object {
        val DEFAULT = APPLE

        fun getFruitByName(name: String): Fruit {
            return when(name) {
                "apple" -> APPLE
                "banana" -> BANANA
                "strawberry" -> STRAWBERRY
                else -> DEFAULT
            }
        }
    }
}