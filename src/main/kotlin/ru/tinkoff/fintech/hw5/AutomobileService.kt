package ru.tinkoff.fintech.hw5


class AutomobileService {
    fun internationalFormatSort(list: List<Automobile>): List<Automobile> =
        list.asSequence().sortedBy { it.price }.map { Convertor().convert(it) }.toList()

    fun bodyGrouping(list: List<Automobile>): Map<String, List<Automobile>> = list.groupBy { it.body }

    fun yearFiltration(list: List<Automobile>, maxYear: Int): List<String> =
        list.asSequence().sortedBy { it.year }.filter { it.year >= maxYear }
            .map { "${it.model} ${it.make} was assembled in ${it.year}" }
            .take(3).toList()
}