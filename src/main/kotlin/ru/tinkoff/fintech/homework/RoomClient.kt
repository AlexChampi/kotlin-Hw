package ru.tinkoff.fintech.homework

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.getForObject
import org.springframework.web.client.*
import org.springframework.web.client.HttpClientErrorException.NotFound
import kotlin.reflect.jvm.internal.impl.util.ModuleVisibilityHelper.EMPTY

@Service
class RoomClient(
    private val restTemplate: RestTemplate,
    @Value("\${room.address}") private val roomAddress: String
) {
    fun getRoomForCheckIn(): Set<Room> =
        restTemplate.exchange<Set<Room>>("$roomAddress$GET_FREE_ROOM", HttpMethod.GET).body.orEmpty()


    fun getRoomByNumber(number: Int): Room =
        restTemplate.getForObject("$roomAddress$GET_ROOM_BY_NUMBER", number)!!
}


private const val GET_FREE_ROOM = "/room/type"
private const val GET_ROOM_BY_NUMBER = "/room/{number}"