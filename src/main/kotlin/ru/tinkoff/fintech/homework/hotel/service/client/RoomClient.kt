package ru.tinkoff.fintech.homework.hotel.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.exchange
import org.springframework.web.client.getForObject
import org.springframework.web.client.*
import org.springframework.web.client.HttpClientErrorException.NotFound
import ru.tinkoff.fintech.homework.model.Room

@Service
class RoomClient(
    private val restTemplate: RestTemplate,
    @Value("\${room.address}") private val roomAddress: String
) {
    fun getRoomByType(type: String): Set<Room?> {
        val response = restTemplate.getForEntity<Set<Room?>>(
            "$roomAddress$GET_ROOM_BY_TYPE",
            type
        )
        return response.body!!
    }

    fun getRoom(number: Int): Room? =
        try {
            restTemplate.getForObject("$roomAddress$GET_ROOM_BY_NUMBER", number)
        } catch (e: NotFound) {
            null
        }

    fun checkOut(number: Int, room: Room) {
        val newRoom = room.copy(status = "free")
        val url = "$roomAddress$/room/$number"
        restTemplate.put(url, room, newRoom)

    }

    fun checkIn(number: Int, room: Room) {
        val newRoom = room.copy(status = "occupied")
        val url = "$roomAddress$/room/$number"
        restTemplate.put(url, room, newRoom)
    }


}


private const val GET_ROOM_BY_TYPE = "/room/type={type}"
private const val GET_ROOM_BY_NUMBER = "/room/{number}"
