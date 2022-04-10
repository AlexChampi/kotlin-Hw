package ru.tinkoff.fintech.homework.hotel.room

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.tinkoff.fintech.homework.hotel.room.service.RoomService
import ru.tinkoff.fintech.homework.hotel.common.model.Room

@RestController
@RequestMapping("/room")
class RoomController(private val roomService: RoomService) {
    @GetMapping()
    fun getRoomByType(@RequestParam type: String): Set<Room> =
        roomService.getRoomByType(type)


    @GetMapping("/{number}")
    fun getRoom(@PathVariable number: Int): Room =
        roomService.getRoom(number)

    @PatchMapping("/{number}")
    fun changeStatus(@PathVariable number: Int, @RequestBody status: String) {
        roomService.changeStatus(number, status)
    }


}