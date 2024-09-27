package ru.tinkoff.fintech.homework.washing.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.washing.model.Order
import ru.tinkoff.fintech.homework.washing.model.Services
import ru.tinkoff.fintech.homework.washing.service.client.Ledger

@Service
class Accounting(private val ledger: Ledger) {
    fun orderPizza(services: Services, cash: Double): Pair<Order, Double> {
        val price = ledger.getPrice(services.bodyStyle)
        return order(services, price, cash)

    }

    private fun order(services: Services, price: Double, money: Double): Pair<Order, Double> {
        require(money >= price) { "Недостаточно денег!" }
        val orderId = ledger.saveOrder(price)
        val order = Order(orderId, services)
        val change = money - price
        return order to change
    }

}
