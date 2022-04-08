package ru.tinkoff.fintech.homework.mockktest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowAny
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
import ru.tinkoff.fintech.homework.model.Room
import ru.tinkoff.fintech.homework.hotel.service.client.RoomClient


@SpringBootTest
@AutoConfigureMockMvc
class HotelTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : FeatureSpec() {

    @MockkBean
    private lateinit var roomClient: RoomClient
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun beforeEach(testCase: TestCase) {
        every { roomClient.getRoomByType(any()) } answers { listOfRoom.filter { it.type == firstArg() }.toSet() }
        every { roomClient.checkIn(any(), any()) } answers {
            listOfRoom.filter { it == secondArg() }.first().status = "occupied"
        }
        every { roomClient.checkOut(any(), any()) } answers {
            listOfRoom.filter { it == secondArg() }.first().status = "free"
        }
        every { roomClient.getRoom(any()) } answers { listOfRoom.find { it.number == firstArg() } }


    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
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
            scenario("occupied") {
                shouldThrowAny { checkIn("deluxe") }
            }

        }
        feature("check out") {
            scenario("check in after check out") {

                shouldNotThrowAny { checkOut(1) }

                val room = checkIn("standard")

                room should {
                    it.number shouldBe 1
                    it.type shouldBe "standard"
                    it.pricePerNight shouldBe 10.0
                    it.status shouldBe "occupied"
                }
            }
        }
    }

    fun checkIn(type: String, status: HttpStatus = HttpStatus.OK): Room =
        mockMvc.post("/hotel/check-in?type={type}", type).readResponse(status)

    fun checkOut(number: Int, status: HttpStatus = HttpStatus.OK): Room =
        mockMvc.post("/hotel/check-out?number={number}", number).readResponse(status)

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