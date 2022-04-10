package ru.tinkoff.fintech.homework.hotel.room.service.client

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.model.Room

@Service
class RoomDao(
    private val rooms: Set<Room>
) {
    fun getRoomByType(type: String): Set<Room> =
        rooms.filter { it.type == type }.toSet()

    fun getRoom(number: Int): Room? =
        rooms.find { it.number == number }

    fun changeStatus(number: Int, newStatus: String) {
        rooms.find { it.number == number }!!.status = newStatus
    }


}