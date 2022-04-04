package ru.tinkoff.fintech.homework

import org.springframework.stereotype.Service


@Service
class Reception(private val roomClient: RoomClient) {
    fun checkIn(type: String): Room {
        val setOfRoom = roomClient.getRoomForCheckIn()
        val result = setOfRoom.find { it.type == type && it.status == "free" } ?: return Room(null, null, null, null)
        result.status = "occupied"
        return result
    }

    fun checkOut(number: Int): Boolean {
        val room = roomClient.getRoomByNumber(number)
        room.status = "free"
        return true
    }

}