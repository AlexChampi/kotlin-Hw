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
import ru.tinkoff.fintech.homework.hotel.reception.service.client.RoomClient


@SpringBootTest
@AutoConfigureMockMvc
class HotelTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : FeatureSpec() {

    @MockkBean
    private lateinit var roomDAO: RoomDao

    @MockkBean
    private lateinit var roomClient: RoomClient

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun beforeEach(testCase: TestCase) {
        every { roomDAO.getRoom(any()) } answers { listOfRoom.find { it.number == firstArg() } }
        every { roomDAO.getRoomByType(any()) } answers { listOfRoom.filter { it.status == firstArg() }.toSet() }
        every { roomDAO.changeStatus(any(), any()) } answers {
            listOfRoom.toMutableSet().find { it.number == firstArg() }!!.status = secondArg()
        }

        every { roomClient.getRoom(any()) } answers { roomDAO.getRoom(firstArg()) }
        every { roomClient.getRoomByType(any()) } answers { roomDAO.getRoomByType(firstArg()) }
        every { roomClient.changeStatus(any(), any()) } answers { roomDAO.changeStatus(firstArg(), secondArg()) }
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
                    it.status shouldBe "free"
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
                    it.status shouldBe "occupied"
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
                    it.status shouldBe "free"
                }
            }
        }


    }

    fun checkIn(type: String, status: HttpStatus = HttpStatus.OK): Room =
        mockMvc.patch("/hotel/check-in?type={type}", type).readResponse()

    fun checkOut(number: Int, status: HttpStatus = HttpStatus.OK) {
        mockMvc.patch("/hotel/check-out?number={number}", number)
    }

    fun getRoom(number: Int): Room =
        mockMvc.get("/hotel/room/{number}", number).readResponse()


    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T =
        this
            .andExpect { status { isEqualTo(expectedStatus.value()) } }
            .andReturn().response.getContentAsString(Charsets.UTF_8)
            .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }


    var listOfRoom = setOf(
        Room(1, "standard", 10.0, "occupied"),
        Room(2, "standard", 10.0, "free"),
        Room(3, "deluxe", 20.0, "free"),
        Room(4, "family", 15.0, "occupied"),
        Room(5, "superior", 17.0, "free")
    )

}