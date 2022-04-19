package ru.tinkoff.fintech.homework.mockktest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.core.spec.style.FeatureSpec
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.*
import ru.tinkoff.fintech.homework.hotel.common.model.Room
import ru.tinkoff.fintech.homework.hotel.common.model.Status.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("jpa")
class JaoReceptionTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : FeatureSpec() {

    init {
        feature("reception") {
            scenario("get room") {
                val room = getRoom(3)

                room should {
                    it.number shouldBe 3
                    it.type shouldBe "deluxe"
                    it.pricePerNight shouldBe 20.0
                    it.status shouldBe FREE
                }
            }

            scenario("check in") {
                val room = checkIn("deluxe")

                room should {
                    it.number shouldBe 3
                    it.type shouldBe "deluxe"
                    it.pricePerNight shouldBe 20.0
                    it.status shouldBe OCCUPIED
                }
            }
            scenario("check out") {
                checkOut(1)

                val room = getRoom(1)

                room should {
                    it.number shouldBe 1
                    it.type shouldBe "standard"
                    it.pricePerNight shouldBe 10.0
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

}