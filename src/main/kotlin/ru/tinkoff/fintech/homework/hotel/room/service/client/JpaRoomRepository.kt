package ru.tinkoff.fintech.homework.hotel.room.service.client

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.tinkoff.fintech.homework.hotel.common.model.Room
import ru.tinkoff.fintech.homework.hotel.common.model.Status

@Repository
interface JpaRoomRepository : JpaRepository<Room, Int> {
    @Transactional
    @Modifying
    @Query(UPDATE_ROOM_STATUS)
    fun changeStatus(@Param("number") number: Int, @Param("status") status: Status)

    fun findRoomByType(type: String): List<Room>
}

private const val UPDATE_ROOM_STATUS = "update Room r set r.status = :status where r.number = :number"