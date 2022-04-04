package ru.tinkoff.fintech.homework.hotel.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.getForObject
import org.springframework.web.client.*
import ru.tinkoff.fintech.homework.model.Room

@Service
class RoomClient(
    private val restTemplate: RestTemplate,
    @Value("\${room.address}") private val roomAddress: String
) {
    fun getRoomForCheckIn(type: String): Room? =
        restTemplate.getForObject("$roomAddress$GET_FREE_ROOM", type)


    fun getRoomByNumber(number: Int): Room? =
        restTemplate.getForObject("$roomAddress$GET_ROOM_BY_NUMBER", number)

    fun changeStatus(number: Int, status: String) {
        restTemplate.patchForObject<Room>("$roomAddress$PATCH_CHANGE_STATUS", number, status)
    }
}


private const val GET_FREE_ROOM = "/room/{type}"
private const val GET_ROOM_BY_NUMBER = "/room/{number}"
private const val PATCH_CHANGE_STATUS = "/room/{number}&{status}"