package ru.tinkoff.fintech.homework.washing.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.washing.model.OrderResponse
import ru.tinkoff.fintech.homework.washing.model.Services
import ru.tinkoff.fintech.homework.washing.model.Status

@Service
class CarWash(
    private val washService: WashService, private val accounting: Accounting
) {
    fun getSetOfServices(): Set<Services> = washService.getSetOfServices()

    fun getCarToWash(bodyStyle: String, cash: Double): OrderResponse<Int> = try {
        val service = washService.getService(bodyStyle)
        val (order, change) = accounting.orderPizza(service, cash)
        washService.order(order)
        OrderResponse(order.id, Status.IN_PROGRESS, change)
    } catch (e: Exception) {
        OrderResponse(null, Status.DECLINED, cash, e.message)
    }

    fun getCarIfReady(orderId: Int): OrderResponse<Services> {
        val isCarReady = washService.isCarReady(orderId)
        return if (isCarReady) {
            val service = washService.getOrder(orderId)
            OrderResponse(service, Status.READY)
        } else {
            OrderResponse(item = null, Status.IN_PROGRESS)
        }
    }


}