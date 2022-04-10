package ru.tinkoff.fintech.homework.hotel.common.model

class Room(
    val number: Int,
    val type: String,
    val pricePerNight: Double,
    var status: String
)