package ru.tinkoff.fintech.homework.hotel

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.tinkoff.fintech.homework.hotel.service.Reception
import ru.tinkoff.fintech.homework.model.Room

@RestController
@RequestMapping("/hotel")
class HotelController(private val reception: Reception) {

    @GetMapping("/check-in")
    fun checkIn(@RequestParam type: String): Room? =
        reception.checkIn(type)


    @GetMapping("/check-out")
    fun checkOut(@RequestParam number: Int): Room =
        reception.checkOut(number)

    @PatchMapping("/change-status")
    fun changeStatus(@RequestParam number: Int, @RequestParam status: String) {
        reception.changeStatus(number, status)
    }
}