package ru.tinkoff.fintech.homework.model

data class Room(
    val number: Int,
    val type: String,
    val pricePerNight: Double,
    var status: String
)