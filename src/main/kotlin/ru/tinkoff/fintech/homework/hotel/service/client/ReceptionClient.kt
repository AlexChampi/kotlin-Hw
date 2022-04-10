package ru.tinkoff.fintech.homework.hotel.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.getForObject
import org.springframework.web.client.*
import org.springframework.web.client.HttpClientErrorException.NotFound
import ru.tinkoff.fintech.homework.model.Room

@Service
class ReceptionClient(
    private val restTemplate: RestTemplate,
    @Value("\${room.address}") private val roomAddress: String
) {
    fun getRoomByType(type: String): Set<Room> = restTemplate.exchange<Set<Room>>(
        "$roomAddress/room/by-type?type=${type}",
        HttpMethod.GET
    ).body.orEmpty()


    fun getRoom(number: Int): Room? =
        try {
            restTemplate.getForObject("$roomAddress/room/by-number?number={number}", number)
        } catch (e: NotFound) {
            null
        }

    fun changeStatus(room: Room, status: String) {
        restTemplate.patchForObject<Room?>("$roomAddress/room/change-status?newStatus={status}", room, status)
    }


}
