package ru.tinkoff.fintech.homework


import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(MockKExtension::class)
class FarmKtMockTest {

    @Test
    fun `a`() {
        val animal = mockk<Animal>()
        every { animal.getName() } returns "Animal"

        val farm = Farm(arrayOf(animal))
        val status = farm.showNames()
        assertAll(
            { assertEquals(true, status) },
            { verify(exactly = farm.countAnimal()) { animal.getName() } }
        )
    }
}