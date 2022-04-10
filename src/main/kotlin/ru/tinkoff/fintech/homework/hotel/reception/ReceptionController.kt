package ru.tinkoff.fintech.homework.hotel.reception

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.tinkoff.fintech.homework.hotel.reception.service.ReceptionService
import ru.tinkoff.fintech.homework.hotel.common.model.Room

@RestController
@RequestMapping("/reception")
class ReceptionController(private val reception: ReceptionService) {

    @PostMapping("/check-in")
    fun checkIn(@RequestParam type: String): Room =
        reception.checkIn(type)

    @PostMapping("/check-out")
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