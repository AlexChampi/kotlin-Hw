package ru.tinkoff.fintech.homework.hotel.room.service.client

import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.hotel.common.model.Room
import ru.tinkoff.fintech.homework.hotel.common.model.Status

@Service
@Profile("jdbc")
class JdbcRoomDao(
    private val jdbcTemplate: JdbcTemplate
) : RoomDao {
    override fun getRoomsByType(type: String): Set<Room> =
        jdbcTemplate.query(GET_ROOMS_BY_TYPE, DataClassRowMapper(Room::class.java), type).toSet()

    override fun getRoom(number: Int): Room? =
        jdbcTemplate.queryForObject(GET_ROOM_BY_NUMBER, DataClassRowMapper(Room::class.java), number)

    override fun changeStatus(number: Int, newStatus: Status) {
        jdbcTemplate.update(UPDATE_STATUS, newStatus.name, number)
    }
}

private const val GET_ROOMS_BY_TYPE = "select * from rooms where type = ?"
private const val GET_ROOM_BY_NUMBER = "select * from rooms where number = ?"
private const val UPDATE_STATUS = "update rooms set status = ? where number = ?"