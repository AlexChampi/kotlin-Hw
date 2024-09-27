package ru.tinkoff.fintech.homework.washing.model

data class Order(
    val id: Int,
    val services: Services,
    val status: Status = Status.IN_PROGRESS
)

data class OrderResponse<T>(
    val item: T? = null,
    val status: Status,
    val change: Double = 0.0,
    val comment: String? = null
)

enum class Status { IN_PROGRESS, READY, DECLINED }
