package ru.tinkoff.fintech.hw5

class Dictionary {
    private val dict =
        mapOf(
            "Форд" to "Ford",
            "Бентли" to "Bentley",
            "БМВ" to "BMW",
            "Лада" to "Lada",
            "Мини" to "Mini"
        )

    fun getWord(string: String): String = dict[string]!!

}