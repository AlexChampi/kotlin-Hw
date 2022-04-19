package ru.tinkoff.fintech.homework.hotel.room.service.client

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.tinkoff.fintech.homework.hotel.common.model.Room
import ru.tinkoff.fintech.homework.hotel.common.model.Status

@Repository
interface JpaRoomRepository : JpaRepository<Room, Int> {

    @Query("update Room r set r.status = :status where r.number = :number")
    fun changeStatus(@Param("number") number: Int, @Param("status") status: Status)

    @Query("select r from Room r where r.status = :type")
    fun getRoomByType(@Param("type") type: String): MutableList<Room>

    @Query("select r from Room r where r.number = :number")
    fun getRoomByNumber(@Param("number") number: Int): Room
}

