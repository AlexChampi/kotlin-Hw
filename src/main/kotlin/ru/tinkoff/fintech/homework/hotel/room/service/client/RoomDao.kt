package ru.tinkoff.fintech.homework.hotel.room.service.client

import ru.tinkoff.fintech.homework.hotel.common.model.Room
import ru.tinkoff.fintech.homework.hotel.common.model.Status

interface RoomDao {
    fun getRoomsByType(type: String): Set<Room>

    fun getRoom(number: Int): Room?

    fun changeStatus(number: Int, newStatus: Status)
}