package ru.tinkoff.fintech.homework.hotel.common.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "rooms")
data class Room(
    @Id
    val number: Int,
    val type: String,
    val pricePerNight: Double,
    var status: Status
)

enum class Status {
    FREE, OCCUPIED
}
