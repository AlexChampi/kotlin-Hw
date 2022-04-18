package ru.tinkoff.fintech.homework.hotel.room.service.client

import org.springframework.context.annotation.Profile
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.hotel.common.model.Room
import ru.tinkoff.fintech.homework.hotel.common.model.Status

@Service
@Profile("jpa")
class JpaRoomDao(
    private val jpaRoomRepository: JpaRoomRepository
) : RoomDao {
    override fun getRoomsByType(type: String): Set<Room> =
        jpaRoomRepository.findAll().filter { it.type == type }.toSet()


    override fun getRoom(number: Int): Room =
        jpaRoomRepository.getById(number)


    override fun changeStatus(number: Int, newStatus: Status) {
        jpaRoomRepository.changeStatus(newStatus, number)
    }
}