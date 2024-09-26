package ru.tinkoff.fintech.hw5


class AutomobileService(private val convertor: Convertor) {

    fun internationalFormatSort(list: List<Automobile>): List<Automobile> =
        list.asSequence().sortedBy { it.price }.map { convertor.convert(it) }.toList()

    fun bodyGrouping(list: List<Automobile>): Map<String, List<Automobile>> = list.groupBy { it.body }

    fun yearFiltration(list: List<Automobile>, minYear: Int): List<String> =
        list.asSequence().sortedBy { it.year }.filter { it.year >= minYear }
            .map { "${it.model} ${it.make} was assembled in ${it.year}" }
            .take(3).toList()
}