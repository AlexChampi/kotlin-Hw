package ru.tinkoff.fintech.homework.hotel

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.tinkoff.fintech.homework.hotel.service.Reception
import ru.tinkoff.fintech.homework.model.Room

@RestController
@RequestMapping("/hotel")
class HotelController(private val reception: Reception) {

    @PatchMapping("/check-in")
    fun checkIn(@RequestParam type: String): Room =
        reception.checkIn(type)

    @PatchMapping("/check-out")
    fun checkOut(@RequestParam number: Int) {
        reception.checkOut(number)
    }

    @GetMapping("/rooms")
    fun getRoomByType(@RequestParam type: String): Set<Room> =
        reception.getRoomByType(type)

    @GetMapping("/room/{number}")
    fun getRoom(@PathVariable number: Int): Room =
        reception.getRoom(number)

}