package ru.tinkoff.fintech.homework.hotel.reception.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.getForObject
import org.springframework.web.client.*
import org.springframework.web.client.HttpClientErrorException.NotFound
import ru.tinkoff.fintech.homework.hotel.common.model.Room
import ru.tinkoff.fintech.homework.hotel.common.model.Status

@Service
class RoomClient(
    private val restTemplate: RestTemplate,
    @Value("\${room.address}") private val roomAddress: String
) {
    fun getRoomsByType(type: String): Set<Room> = restTemplate.exchange<Set<Room>>(
        "$roomAddress/room?type={type}",
        HttpMethod.GET,
        null,
        type
    ).body.orEmpty()


    fun getRoom(number: Int): Room? =
        try {
            restTemplate.getForObject("$roomAddress/room/{number}", number)
        } catch (e: NotFound) {
            null
        }

    fun changeStatus(number: Int, status: Status) {
        restTemplate.patchForObject<Void>("$roomAddress/room/{number}", status, number)
    }


}
