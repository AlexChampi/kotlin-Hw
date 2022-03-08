package ru.tinkoff.fintech.homework

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class FarmKtUnitTest {

    @Test
    @DisplayName("CowTest")
    fun `testCow`() {
        val name = "Marusyia"
        val cow = Cow(name);
        assertEquals(name, cow.getName());
    }
}