package ru.tinkoff.fintech.homework.hotel.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.hotel.service.client.RoomClient
import ru.tinkoff.fintech.homework.model.Room


@Service
class Reception(private val roomClient: RoomClient) {
    fun checkIn(type: String): Room {
        val room = getRoomByType(type).find { it.status == "free" }
        requireNotNull(room) { "No available room" }
        roomClient.checkIn(room.number, room)
        return getRoom(room.number)
    }

    fun checkOut(number: Int) {
        val room = getRoom(number)
        roomClient.checkOut(number, room)
    }

    fun getRoomByType(type: String): Set<Room> {
        val result = roomClient.getRoomByType(type)
        result.forEach { requireNotNull(it) { "No such type of room" } }
        return result as Set<Room>
    }

    fun getRoom(number: Int): Room {
        val room = roomClient.getRoom(number)
        requireNotNull(room) { "No such room" }
        return room
    }

}