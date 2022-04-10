package ru.tinkoff.fintech.homework.hotel.common.model

data class Room(
    val number: Int,
    val type: String,
    val pricePerNight: Double,
    var status: Status
)

enum class Status {
    FREE, OCCUPIED
}
