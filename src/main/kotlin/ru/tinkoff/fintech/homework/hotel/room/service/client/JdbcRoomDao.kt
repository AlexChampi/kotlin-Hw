package ru.tinkoff.fintech.homework.hotel.room.service.client

import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.hotel.common.model.Room
import ru.tinkoff.fintech.homework.hotel.common.model.Status

@Service
@Profile("jdbc")
class JdbcRoomDao(
    private val jdbcTemplate: JdbcTemplate
) : RoomDao {
    override fun getRoomsByType(type: String): Set<Room> =
        jdbcTemplate.query("select * from rooms where type = ?", DataClassRowMapper(Room::class.java), type).toSet()


    override fun getRoom(number: Int): Room? =
        jdbcTemplate.queryForObject("select * from rooms where number = ?", DataClassRowMapper(Room::class.java), number)

    override fun changeStatus(number: Int, newStatus: Status) {
        jdbcTemplate.update("update rooms set status = '${newStatus}' where number = ?", number)
    }
}

