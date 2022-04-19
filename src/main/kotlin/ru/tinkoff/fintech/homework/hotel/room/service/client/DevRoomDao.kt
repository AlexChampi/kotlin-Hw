package ru.tinkoff.fintech.homework.hotel.room.service.client

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.hotel.common.model.Room
import ru.tinkoff.fintech.homework.hotel.common.model.Status

@Service
@Profile("dev")
class DevRoomDao(
    private val rooms: Set<Room>
) : RoomDao {
    override fun getRoomsByType(type: String): Set<Room> =
        rooms.filter { it.type == type }.toSet()

    override fun getRoom(number: Int): Room? =
        rooms.find { it.number == number }

    override fun changeStatus(number: Int, newStatus: Status) {
        rooms.first { it.number == number }.status = newStatus
    }


}