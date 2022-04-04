package ru.tinkoff.fintech.homework.hotel.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.hotel.service.client.RoomClient
import ru.tinkoff.fintech.homework.model.Room


@Service
class Reception(private val roomClient: RoomClient) {
    fun checkIn(type: String): Room {
        val result = roomClient.getRoomForCheckIn(type)
        requireNotNull(result) { "No such room" }
        return result
    }

    fun checkOut(number: Int): Room {
        val room = roomClient.getRoomByNumber(number)
        requireNotNull(room) { "No such room" }
        return room
    }

    fun changeStatus(number: Int, status: String) {
        roomClient.changeStatus(number, status)
    }

}