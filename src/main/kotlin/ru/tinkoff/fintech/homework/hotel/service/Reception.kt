package ru.tinkoff.fintech.homework.hotel.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.hotel.service.client.ReceptionClient
import ru.tinkoff.fintech.homework.model.Room


@Service
class Reception(private val receptionClient: ReceptionClient) {
    fun checkIn(type: String): Room {
        val room = getRoomByType(type).find { it.status == "free" }!!
        //requireNotNull(room) { "No available room" }
        receptionClient.changeStatus(room, "occupied")
        return getRoom(room.number)
    }

    fun checkOut(number: Int) {
        val room = getRoom(number)
        receptionClient.changeStatus(room, "free")
    }

    fun getRoomByType(type: String): Set<Room> {
        val result = receptionClient.getRoomByType(type)
        result.forEach { requireNotNull(it) { "No such type of room" } }
        return result as Set<Room>
    }

    fun getRoom(number: Int): Room {
        val room = receptionClient.getRoom(number)
        requireNotNull(room) { "No such room" }
        return room
    }

}