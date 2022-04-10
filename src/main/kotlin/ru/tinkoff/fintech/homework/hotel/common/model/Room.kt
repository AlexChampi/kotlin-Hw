package ru.tinkoff.fintech.homework.hotel.common.model

enum class Status {
    FREE, OCCUPIED
}

class Room(
    val number: Int,
    val type: String,
    val pricePerNight: Double,
    var status: Status
)