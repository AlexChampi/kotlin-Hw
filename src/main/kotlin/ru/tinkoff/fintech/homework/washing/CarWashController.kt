package ru.tinkoff.fintech.homework.washing

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.washing.model.OrderResponse
import ru.tinkoff.fintech.homework.washing.model.Services
import ru.tinkoff.fintech.homework.washing.service.CarWash


@RestController
@RequestMapping("/car-wash")
class CarWashController(private val carWashStore: CarWash) {

    @GetMapping("/prices")
    fun getPrices(): Set<Services> = carWashStore.getSetOfServices()

    @PostMapping("/order")
    fun giveCarToWash(@RequestParam bodyStyle: String, @RequestParam cash: Double): OrderResponse<Int> =
        carWashStore.getCarToWash(bodyStyle, cash)

    @GetMapping("/order/{orderId}")
    fun getCarIfReady(@PathVariable orderId: Int): OrderResponse<Services> = carWashStore.getCarIfReady(orderId)

}

