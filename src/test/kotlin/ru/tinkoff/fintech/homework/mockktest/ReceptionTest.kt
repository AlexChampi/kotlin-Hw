package ru.tinkoff.fintech.homework.mockktest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.mockk.clearAllMocks
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import io.kotest.core.extensions.Extension
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.*
import ru.tinkoff.fintech.homework.hotel.room.service.client.RoomDao
import ru.tinkoff.fintech.homework.hotel.common.model.Room
import ru.tinkoff.fintech.homework.hotel.common.model.Status.*
import ru.tinkoff.fintech.homework.hotel.reception.service.client.RoomClient


@SpringBootTest
@AutoConfigureMockMvc
class ReceptionTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : FeatureSpec() {

    @MockkBean
    private lateinit var roomDao: RoomDao

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun beforeEach(testCase: TestCase) {
        every { roomDao.getRoom(any()) } answers { listOfRoom.find { it.number == firstArg() } }
        every { roomDao.getRoomsByType(any()) } answers { listOfRoom.filter { it.status == firstArg() }.toSet() }
        every { roomDao.changeStatus(any(), any()) } answers {
            listOfRoom.toMutableSet().find { it.number == firstArg() }!!.status = secondArg()
        }
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        feature("get room") {
            scenario("success") {
                val room = getRoom(3)

                room should {
                    it.number shouldBe 3
                    it.type shouldBe "deluxe"
                    it.pricePerNight shouldBe 20.0
                    it.status shouldBe FREE
                }
            }
        }
        feature("check in") {
            scenario("success") {
                val room = checkIn("deluxe")

                room should {
                    it.number shouldBe 3
                    it.type shouldBe "deluxe"
                    it.pricePerNight shouldBe 20.0
                    it.status shouldBe OCCUPIED
                }
            }
        }
        feature("check out") {
            scenario("success") {
                checkOut(1)

                val room = getRoom(3)

                room should {
                    it.number shouldBe 3
                    it.type shouldBe "deluxe"
                    it.pricePerNight shouldBe 20.0
                    it.status shouldBe FREE
                }
            }
        }


    }

    fun checkIn(type: String, status: HttpStatus = HttpStatus.OK): Room =
        mockMvc.post("/reception/check-in?type={type}", type).readResponse()

    fun checkOut(number: Int, status: HttpStatus = HttpStatus.OK) {
        mockMvc.post("/reception/check-out?number={number}", number)
    }

    fun getRoom(number: Int): Room =
        mockMvc.get("/reception/room/{number}", number).readResponse()


    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T =
        this
            .andExpect { status { isEqualTo(expectedStatus.value()) } }
            .andReturn().response.getContentAsString(Charsets.UTF_8)
            .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }


    var listOfRoom = setOf(
        Room(1, "standard", 10.0, OCCUPIED),
        Room(2, "standard", 10.0, FREE),
        Room(3, "deluxe", 20.0, FREE),
        Room(4, "family", 15.0, OCCUPIED),
        Room(5, "superior", 17.0, FREE)
    )

}