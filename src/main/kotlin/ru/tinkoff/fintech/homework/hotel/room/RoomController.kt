package ru.tinkoff.fintech.homework.hotel.room

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.tinkoff.fintech.homework.hotel.room.service.RoomService
import ru.tinkoff.fintech.homework.model.Room

@RestController
@RequestMapping("/room")
class RoomController(private val roomService: RoomService) {
    @GetMapping("/by-type")
    fun getRoomByType(@RequestParam type: String): Set<Room> =
        roomService.getRoomByType(type)


    @GetMapping("/by-number")
    fun getRoom(@RequestParam number: Int): Room =
        roomService.getRoom(number)

    @PatchMapping("/change-status")
    fun changeStatus(@RequestBody room: Room, @RequestParam newStatus: String) {
        roomService.changeStatus(room, newStatus)
    }


}