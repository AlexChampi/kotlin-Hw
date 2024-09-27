package ru.tinkoff.fintech.homework.washing.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.client.getForObject
import ru.tinkoff.fintech.homework.washing.model.Services

@Service
class WashServiceClient(
    private val restTemplate: RestTemplate, @Value("\${wash.prices}") private val washPrices: String
) {
    fun getSetOfServices(): Set<Services> =
        restTemplate.exchange<Set<Services>>("$washPrices$GET_SERVICES", HttpMethod.GET).body.orEmpty()

    fun getService(bodyStyle: String): Services? = try {
        restTemplate.getForObject("$washPrices$GET_SERVICE_BY_BODY_STYLE", bodyStyle.lowercase())
    } catch (e: NotFound) {
        null
    }

}

private const val GET_SERVICES = "/car-wash"
private const val GET_SERVICE_BY_BODY_STYLE = "/car-wash?bodyStyle={bodyStyle}"