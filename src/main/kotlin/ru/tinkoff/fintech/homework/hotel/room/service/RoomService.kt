package ru.tinkoff.fintech.homework.hotel.room.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.hotel.room.service.client.RoomDao
import ru.tinkoff.fintech.homework.model.Room

@Service
class RoomService(private val roomDao: RoomDao) {
    fun getRoomByType(type: String): Set<Room> =
        roomDao.getRoomByType(type)


    fun getRoom(number: Int): Room {
        val result = roomDao.getRoom(number)
        requireNotNull(result) { "Room $number doesn't exists" }
        return result
    }

    fun changeStatus(number: Int, status: String) {
        roomDao.changeStatus(number, status)
    }
}