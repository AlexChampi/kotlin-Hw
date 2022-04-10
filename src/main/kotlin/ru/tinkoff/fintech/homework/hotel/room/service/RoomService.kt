package ru.tinkoff.fintech.homework.hotel.room.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.hotel.room.service.client.RoomClient
import ru.tinkoff.fintech.homework.model.Room

@Service
class RoomService(private val roomClient: RoomClient) {
    fun getRoomByType(type: String): Set<Room> =
        roomClient.getRoomByType(type)


    fun getRoom(number: Int): Room {
        val result = roomClient.getRoom(number)
        requireNotNull(result) { "Room $number doesn't exists" }
        return result
    }

    fun changeStatus(room: Room, newStatus: String) {
        roomClient.changeStatus(room, newStatus)
    }
}