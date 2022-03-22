package ru.tinkoff.fintech.hw5

class Convertor {
    private val curse = 100
    fun convert(automobile: Automobile): Automobile =
        Automobile(
            Dictionary().getWord(automobile.model),
            automobile.make,
            automobile.body,
            automobile.price / curse,
            automobile.gasMileage,
            automobile.year
        )
}