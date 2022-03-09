package ru.tinkoff.fintech.homework

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class FarmKtUnitTest {

    private val cowName = "Marusyia"
    private val cow = Cow(cowName)

    @Test
    @DisplayName("Cow name")
    fun `cow name test`() {
        assertEquals(cowName, cow.getName());
    }

    private val pigName = "Pepa"
    private val pigWeight = 150
    private val pig = Pig(pigName, pigWeight)

    @Test
    @DisplayName("Pig name")
    fun `pig name tests`() {
        assertEquals(pigName, pig.getName())
    }

    @Test
    @DisplayName("Pig weight")
    fun `pig weight tests`() {
        assertEquals(pigWeight, pig.getWeight())
    }

}