package ru.tinkoff.fintech.hw5

class Convertor(private val dictionary: Dictionary, private val rate: Int = 100) {

    fun convert(automobile: Automobile): Automobile =
        Automobile(
            dictionary.getWord(automobile.model),
            automobile.make,
            automobile.body,
            automobile.price / rate,
            automobile.gasMileage,
            automobile.year
        )
}