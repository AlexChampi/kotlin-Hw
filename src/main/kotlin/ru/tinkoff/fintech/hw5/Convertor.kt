package ru.tinkoff.fintech.hw5

class Convertor(private val dictionary: Dictionary, private val rate: Int = 100) {

    fun convert(automobile: Automobile): Automobile =
        automobile.copy(model = dictionary.getWord(automobile.model), price = automobile.price / rate)

}