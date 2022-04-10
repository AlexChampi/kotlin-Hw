package ru.tinkoff.fintech.homework.hotel.room

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.tinkoff.fintech.homework.hotel.room.service.RoomService
import ru.tinkoff.fintech.homework.hotel.common.model.Room
import ru.tinkoff.fintech.homework.hotel.common.model.Status

@RestController
@RequestMapping("/room")
class RoomController(private val roomService: RoomService) {
    @GetMapping
    fun getRoomsByType(@RequestParam type: String): Set<Room> =
        roomService.getRoomsByType(type)


    @GetMapping("/{number}")
    fun getRoom(@PathVariable number: Int): Room =
        roomService.getRoom(number)

    @PatchMapping("/{number}")
    fun changeStatus(@PathVariable number: Int, @RequestParam status: Status) {
        roomService.changeStatus(number, status)
    }


}