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
    fun `Farm mockk tests`() {
        val animal = mockk<Animal>()
        every { animal.getName() } returns "Animal"

        val animalArray = arrayOf(animal)
        val farm = Farm(animalArray)

        farm.showNames()

        assertAll(
            { assertEquals(animalArray.size, farm.countAnimal()) },
            { verify(exactly = farm.countAnimal()) { animal.getName() } }
        )
    }
}