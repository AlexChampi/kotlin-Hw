package ru.tinkoff.fintech.homework.mockktest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import ru.tinkoff.fintech.homework.hotel.common.model.Room
import ru.tinkoff.fintech.homework.hotel.common.model.Status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@DirtiesContext
abstract class ReceptionTests(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : FeatureSpec() {

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    fun runTests() {
        feature("reception") {
            scenario("get room") {
                val room = getRoom(3)

                room should {
                    it.number shouldBe 3
                    it.type shouldBe "deluxe"
                    it.pricePerNight shouldBe 20.0
                    it.status shouldBe Status.FREE
                }
            }

            scenario("check in") {
                val room = checkIn("deluxe")

                room should {
                    it.number shouldBe 3
                    it.type shouldBe "deluxe"
                    it.pricePerNight shouldBe 20.0
                    it.status shouldBe Status.OCCUPIED
                }
            }
            scenario("check out") {
                checkOut(1)

                val room = getRoom(1)

                room should {
                    it.number shouldBe 1
                    it.type shouldBe "standard"
                    it.pricePerNight shouldBe 10.0
                    it.status shouldBe Status.FREE
                }
            }
        }
    }

    private fun checkIn(type: String, status: HttpStatus = HttpStatus.OK): Room =
        mockMvc.post("/reception/check-in?type={type}", type).readResponse()

    private fun checkOut(number: Int, status: HttpStatus = HttpStatus.OK) {
        mockMvc.post("/reception/check-out?number={number}", number)
    }

    private fun getRoom(number: Int): Room =
        mockMvc.get("/reception/room/{number}", number).readResponse()

    private inline
    fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T =
        this
            .andExpect { status { isEqualTo(expectedStatus.value()) } }
            .andReturn().response.getContentAsString(Charsets.UTF_8)
            .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }

}