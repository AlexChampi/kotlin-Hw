package ru.tinkoff.fintech.homework.mockktest

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.mockk.clearAllMocks
import io.kotest.core.extensions.Extension
import io.kotest.extensions.spring.SpringExtension
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.*
import ru.tinkoff.fintech.homework.hotel.room.service.client.RoomDao
import ru.tinkoff.fintech.homework.hotel.common.model.Room
import ru.tinkoff.fintech.homework.hotel.common.model.Status.*


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@AutoConfigureMockMvc
//@EnableAsync
@ActiveProfiles("dev")
class DevReceptionTest(mockMvc: MockMvc, objectMapper: ObjectMapper) :
    ReceptionTests(mockMvc, objectMapper) {

    @MockkBean
    private lateinit var roomDao: RoomDao

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override suspend fun beforeEach(testCase: TestCase) {
        every { roomDao.getRoom(any()) } answers { listOfRoom.find { it.number == firstArg() } }
        every { roomDao.getRoomsByType(any()) } answers { listOfRoom.filter { it.type == firstArg() }.toSet() }
        every { roomDao.changeStatus(any(), any()) } answers {
            listOfRoom.toMutableSet().find { it.number == firstArg() }!!.status = secondArg()
        }
    }

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        runTests()
    }


    var listOfRoom = setOf(
        Room(1, "standard", 10.0, OCCUPIED),
        Room(2, "standard", 10.0, FREE),
        Room(3, "deluxe", 20.0, FREE),
        Room(4, "family", 15.0, OCCUPIED),
        Room(5, "superior", 17.0, FREE)
    )

}

