package ru.tinkoff.fintech.homework.hotel.reception.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.hotel.reception.service.client.RoomClient
import ru.tinkoff.fintech.homework.hotel.common.model.Room
import ru.tinkoff.fintech.homework.hotel.common.model.Status.FREE
import ru.tinkoff.fintech.homework.hotel.common.model.Status.OCCUPIED

@Service
class ReceptionService(private val roomClient: RoomClient) {
    fun checkIn(type: String): Room {
        val room = getRoomByType(type).find { it.status == FREE }
        requireNotNull(room) { "No available room" }
        roomClient.changeStatus(room.number, OCCUPIED)
        return getRoom(room.number)
    }

    fun checkOut(number: Int) {
        roomClient.changeStatus(number, FREE)
    }

    fun getRoomByType(type: String): Set<Room> {
        val result = roomClient.getRoomsByType(type)
        result.forEach { requireNotNull(it) { "No such type of room" } }
        return result
    }

    fun getRoom(number: Int): Room {
        val room = roomClient.getRoom(number)
        requireNotNull(room) { "No such room" }
        return room
    }

}