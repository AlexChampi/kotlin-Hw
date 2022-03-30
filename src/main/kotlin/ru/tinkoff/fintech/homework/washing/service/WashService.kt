package ru.tinkoff.fintech.homework.washing.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.washing.model.Order
import ru.tinkoff.fintech.homework.washing.model.Services
import ru.tinkoff.fintech.homework.washing.model.Status
import ru.tinkoff.fintech.homework.washing.service.client.WashServiceClient
import java.util.concurrent.ConcurrentHashMap

@Service
class WashService(
    private val washServiceClient: WashServiceClient
) {
    private val log = LoggerFactory.getLogger(javaClass)
    private val orders = ConcurrentHashMap<Int, Order>()

    fun getSetOfServices(): Set<Services> = washServiceClient.getSetOfServices()

    fun getService(bodyStyle: String): Services {
        val services = washServiceClient.getService(bodyStyle)
        return requireNotNull(services) { "мы не обслуживаем такие машины!" }
    }

    fun order(order: Order) {

        CoroutineScope(Dispatchers.Default).launch {
            orders[order.id] = order
            log.info("Автомобиль ${order.id} в работе")
            wash(order.services)
            orders[order.id] = order.copy(status = Status.READY)
            log.info("Автомобиль ${order.id} ожидает")
        }
    }

    private fun wash(services: Services) {
        val time = services.timeInMinutes * 10L
        Thread.sleep(time)
    }

    fun isCarReady(orderId: Int): Boolean {
        val order = orders[orderId]
        requireNotNull(order) { "Нет такой машины!" }
        return order.status == Status.READY
    }

    fun getOrder(orderId: Int): Services {
        val order = orders.remove(orderId)
        requireNotNull(order) { "Нет такой машины!" }
        return order.services
    }
}
