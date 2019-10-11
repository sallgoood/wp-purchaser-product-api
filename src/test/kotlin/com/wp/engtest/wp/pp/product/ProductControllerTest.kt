package com.wp.engtest.wp.pp.product

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wp.engtest.wp.pp.IntegrationTestBase
import com.wp.engtest.wp.pp.product.persistence.ProductRepository
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

internal class ProductControllerTest: IntegrationTestBase() {
    @Autowired
    lateinit var repository: ProductRepository

    @Test
    fun `when enrol new product then data should be persisted`() {
        val name = "anyValidProductName"
        val command = ProductEnrolmentCommand(name)
        val commandJson = jacksonObjectMapper().writeValueAsString(command)

        mvc.perform(
                MockMvcRequestBuilders.post("/products")
                        .content(commandJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)

        val purchaser = repository.findByName(name)
        assertNotNull(purchaser)
        assertNotNull(purchaser.enrolledAt)
    }

    @Test
    fun `when enrol new product with blank name then should return 400`() {
        val name = " "
        val command = ProductEnrolmentCommand(name)
        val commandJson = jacksonObjectMapper().writeValueAsString(command)

        mvc.perform(
                MockMvcRequestBuilders.post("/products")
                        .content(commandJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }
}
