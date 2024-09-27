package ru.tinkoff.fintech.homework.washing.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.client.postForObject
import ru.tinkoff.fintech.homework.washing.model.external.SaveOrderRequest
import ru.tinkoff.fintech.homework.washing.model.external.SaveOrderResponse

@Service
class Ledger(
    private val restTemplate: RestTemplate, @Value("\${ledger.address}") private val ledgerAddress: String
) {

    fun getPrice(bodyStyle: String): Double = restTemplate.getForObject("$ledgerAddress$GET_PRICE", bodyStyle)!!

    fun saveOrder(price: Double): Int {
        val request = SaveOrderRequest(price)
        return restTemplate.postForObject<SaveOrderResponse?>("$ledgerAddress$SAVE_ORDER", request)!!.orderId
    }
}

private const val GET_PRICE = "/price?bodyStyle={bodyStyle}"
private const val SAVE_ORDER = "/order"
