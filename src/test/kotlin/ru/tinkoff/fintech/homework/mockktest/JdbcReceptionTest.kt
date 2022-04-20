package ru.tinkoff.fintech.homework.mockktest

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.*

@ActiveProfiles("jdbc")
class JdbcReceptionTest(mockMvc: MockMvc, objectMapper: ObjectMapper) : ReceptionTests(mockMvc, objectMapper) {

    init {
        runTests()
    }

}