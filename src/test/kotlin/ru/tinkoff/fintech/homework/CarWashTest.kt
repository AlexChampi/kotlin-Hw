package ru.tinkoff.fintech.homework

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import kotlinx.coroutines.delay
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import ru.tinkoff.fintech.homework.washing.model.OrderResponse
import ru.tinkoff.fintech.homework.washing.model.Services
import ru.tinkoff.fintech.homework.washing.model.Status
import ru.tinkoff.fintech.homework.washing.service.client.Ledger
import ru.tinkoff.fintech.homework.washing.service.client.WashServiceClient
import kotlin.random.Random.Default.nextInt

@SpringBootTest
@AutoConfigureMockMvc
class CarWashTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : FeatureSpec() {

    @MockkBean
    private lateinit var ledger: Ledger

    @MockkBean
    private lateinit var washServiceClient: WashServiceClient

    override fun extensions(): List<Extension> = listOf(SpringExtension)


    override fun beforeEach(testCase: TestCase) {
        every { washServiceClient.getSetOfServices() } returns services
        every { washServiceClient.getService(any()) } answers { services.find { it.bodyStyle == firstArg() } }
        every { ledger.saveOrder(any()) } returns nextInt()
        every { ledger.getPrice(any()) } returns SUV_PRICE
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        feature("wash a car") {
            scenario("success") {
                val available = getPrices()
                val service = available.first()
                val cash = service.price + 10.0


                val order = giveCarToWash(service.bodyStyle, cash)
                val orderId = order.item

                orderId.shouldNotBeNull()
                order.change shouldBe (cash - service.price)

                getCarIfReady(orderId) shouldBe OrderResponse(status = Status.IN_PROGRESS)
                delay(1000)
                getCarIfReady(orderId) should {
                    it.item!!.bodyStyle shouldBe service.bodyStyle
                    it.status shouldBe Status.READY
                }
            }

            scenario("failure - unknown body style") {
                val name = "Джип"
                val cash = Double.MAX_VALUE

                val order = giveCarToWash(name, cash)

                order should {
                    it.item shouldBe null
                    it.status shouldBe Status.DECLINED
                    it.change shouldBe cash
                    it.comment shouldBe "мы не обслуживаем такие машины!"
                }
            }
        }
    }


    private fun getPrices(): Set<Services> = mockMvc.get("/car-wash/prices").readResponse()

    private fun giveCarToWash(bodyStyle: String, cash: Double, status: HttpStatus = HttpStatus.OK): OrderResponse<Int> =
        mockMvc.post("/car-wash/order?bodyStyle={bodyStyle}&cash={cash}", bodyStyle, cash).readResponse(status)

    private fun getCarIfReady(orderId: Int): OrderResponse<Services> =
        mockMvc.get("/car-wash/order/{orderId}", orderId).readResponse()

    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T =
        this.andExpect { status { isEqualTo(expectedStatus.value()) } }
            .andReturn().response.getContentAsString(Charsets.UTF_8)
            .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }


    private val services = setOf(
        Services("micro", 10, 200.0), Services("suv", 20, 400.0), Services("van", 50, 800.0)
    )
}

private const val SUV_PRICE = 400.0