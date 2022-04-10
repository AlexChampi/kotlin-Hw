package ru.tinkoff.fintech.homework.hotel.room.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.tinkoff.fintech.homework.model.Room

@Service
class RoomClient(
    private val roomDAO: RoomDAO,
    @Value("\${room.storage.address}") private val roomStorage: String
) {
    fun getRoomByType(type: String): Set<Room> =
        roomDAO.getRoomByType(type)


    fun getRoom(number: Int): Room? =
        roomDAO.getRoom(number)

    fun changeStatus(room: Room, newStatus: String) =
        roomDAO.changeStatus(room.number, newStatus)


}