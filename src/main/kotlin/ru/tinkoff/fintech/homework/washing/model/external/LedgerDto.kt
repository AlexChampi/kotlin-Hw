package ru.tinkoff.fintech.homework.washing.model.external

data class SaveOrderRequest(val price: Double)

data class SaveOrderResponse(val orderId: Int)
