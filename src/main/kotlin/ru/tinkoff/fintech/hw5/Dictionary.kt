package ru.tinkoff.fintech.hw5

class Dictionary(private val dict: Map<String, String>) {
    fun getWord(string: String): String = dict.getValue(string)
}