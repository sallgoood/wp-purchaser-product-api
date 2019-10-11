package com.wp.engtest.wp.pp.purchaser

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wp.engtest.wp.pp.IntegrationTestBase
import com.wp.engtest.wp.pp.product.persistence.Product
import com.wp.engtest.wp.pp.product.persistence.ProductRepository
import com.wp.engtest.wp.pp.purchaser.history.persistence.PurchaseHistory
import com.wp.engtest.wp.pp.purchaser.history.persistence.PurchaseHistoryRepository
import com.wp.engtest.wp.pp.purchaser.persistence.Purchaser
import com.wp.engtest.wp.pp.purchaser.persistence.PurchaserRepository
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate
import java.util.*
import java.util.UUID.randomUUID

internal class PurchaserControllerTest : IntegrationTestBase() {

    @Autowired
    lateinit var purchaserRepository: PurchaserRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var historyRepository: PurchaseHistoryRepository

    @BeforeEach
    fun init() {

    }

    @Test
    fun `when purchaser enrol then data should be persisted`() {
        val name = "anyValidPurchaserName"
        val command = PurchaserEnrolmentCommand(name)
        val commandJson = jacksonObjectMapper().writeValueAsString(command)

        mvc.perform(
                post("/purchasers")
                        .content(commandJson)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful)

        val purchaser = purchaserRepository.findByName(name)
        assertNotNull(purchaser)
        assertNotNull(purchaser.enrolledAt)
    }

    @Test
    fun `when purchaser enrol with blank name then should return 400`() {
        val name = " "
        val command = PurchaserEnrolmentCommand(name)
        val commandJson = jacksonObjectMapper().writeValueAsString(command)

        mvc.perform(
                post("/purchasers")
                        .content(commandJson)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError)
    }

    @Test
    fun `when purchaser purchase product then history should be persisted`() {
        val (purchaserId, _, _) = purchaserRepository.save(Purchaser(name = randomUUID().toString()))
        val (productId, _, _) = productRepository.save(Product(name = randomUUID().toString()))

        val command = PurchaseCommand(productId)
        val commandJson = jacksonObjectMapper().writeValueAsString(command)

        mvc.perform(
                post("/purchasers/{id}/do-purchase", purchaserId)
                        .content(commandJson)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful)

        val histories = historyRepository.findByPurchaserId(purchaserId)
        assertNotNull(histories)
        assertEquals(1, histories.size)
        assertNotNull(histories.first().purchasedAt)
    }

    @Test
    fun `when purchaser purchase product with invalid request then should return 400`() {
        val command = PurchaseCommand(0)
        val commandJson = jacksonObjectMapper().writeValueAsString(command)

        mvc.perform(
                post("/purchasers/{id}/do-purchase", 1)
                        .content(commandJson)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `when purchaser search purchase history then should return histories`() {
        val (id, _, _) = purchaserRepository.save(Purchaser(name = randomUUID().toString()))
        val (productId, productName, _) = productRepository.save(Product(name = randomUUID().toString()))

        val anyDate = LocalDate.of(2019, 10, 11)
        val anyDateTime = anyDate.atStartOfDay()
        val aDayBefore = anyDateTime.minusDays(1)
        val aDayBeforeDate = aDayBefore.toLocalDate()
        val aDayAfter = anyDateTime.plusDays(1)

        historyRepository.saveAll(listOf(
                PurchaseHistory(purchaserId = id, productId = productId, productName = productName, purchasedAt = anyDateTime),
                PurchaseHistory(purchaserId = id, productId = productId, productName = productName, purchasedAt = anyDateTime),
                PurchaseHistory(purchaserId = id, productId = productId, productName = productName, purchasedAt = aDayBefore),
                PurchaseHistory(purchaserId = id, productId = productId, productName = productName, purchasedAt = aDayBefore)))

        mvc.perform(
                get("/purchasers/$id/purchase-histories" +
                        "?start_date=$aDayBefore&end_date=$aDayAfter" +
                        "&page=0&size=10&sort=purchasedAt,desc"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful)
                .andExpect(jsonPath("purchases.$anyDate", Matchers.hasSize<PurchaseHistory>(2)))
                .andExpect(jsonPath("purchases.$aDayBeforeDate", Matchers.hasSize<PurchaseHistory>(2)))
    }
}
