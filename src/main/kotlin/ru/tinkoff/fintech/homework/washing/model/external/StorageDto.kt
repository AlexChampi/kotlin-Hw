package ru.tinkoff.fintech.homework.washing.model.external

data class GetAmountResponse(val ingredient: String, val amount: Int)

data class TakeIngredientRequest(val ingredient: String, val amount: Int)
