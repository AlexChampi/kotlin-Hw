package ru.tinkoff.fintech.homework

import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hotel")
class Hotel(private val reception: Reception) {

    @GetMapping("/check-in")
    fun checkIn(@RequestParam type: String): Room? =
        reception.checkIn(type)


    @GetMapping("/check-out")
    fun checkOut(@RequestParam number: Int): Boolean =
        reception.checkOut(number)

}