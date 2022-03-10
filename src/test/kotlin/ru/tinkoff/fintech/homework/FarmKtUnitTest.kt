package ru.tinkoff.fintech.homework

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


class FarmKtUnitTest {


    @Test
    @DisplayName("Cow name")
    fun `cow name`() {
        val cowName = "Marusyia"
        val cow = Cow(cowName)
        assertEquals(cowName, cow.getName());
    }

    @Test
    fun `pig methods`() {
        val pigName = "Pepa"
        val pigWeight = 150
        val pig = Pig(pigName, pigWeight)
        assertEquals(pigName, pig.getName())
        assertEquals(pigWeight, pig.getWeight())
    }
}
