package ru.tinkoff.fintech.homework.mockktest

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.*


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@AutoConfigureMockMvc
//@EnableAsync
@ActiveProfiles("jpa")
class JaoReceptionTest(mockMvc: MockMvc, objectMapper: ObjectMapper) :
    ReceptionTests(mockMvc, objectMapper) {


    init {
        runTests()
    }
}