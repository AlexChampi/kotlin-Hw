package ru.tinkoff.fintech.homework.hotel.room.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.hotel.room.service.client.RoomDao
import ru.tinkoff.fintech.homework.hotel.common.model.Room
import ru.tinkoff.fintech.homework.hotel.common.model.Status

@Service
class RoomService(private val roomDao: RoomDao) {
    fun getRoomsByType(type: String): Set<Room> =
        roomDao.getRoomByType(type)


    fun getRoom(number: Int): Room {
        val result = roomDao.getRoom(number)
        requireNotNull(result) { "Room $number doesn't exists" }
        return result
    }

    fun changeStatus(number: Int, status: Status) {
        roomDao.changeStatus(number, status)
    }
}