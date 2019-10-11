package com.wp.engtest.wp.pp.purchaser.history

import com.wp.engtest.wp.pp.IntegrationTestBase
import com.wp.engtest.wp.pp.purchaser.history.persistence.PurchaseHistory
import com.wp.engtest.wp.pp.purchaser.history.persistence.PurchaseHistoryRepository
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import java.time.LocalDate

class PurchaserHistoryRepositoryTest: IntegrationTestBase() {

    @Autowired
    lateinit var historyRepository: PurchaseHistoryRepository

    @Test
    fun findByIdAndPurchasedAtBetween() {
        val anyDate = LocalDate.of(2019, 10, 11)
        val anyDateTime = anyDate.atStartOfDay()
        val aDayBefore = anyDateTime.minusDays(1)
        val aDayAfter = anyDateTime.plusDays(1)
        val twoDayAgo = anyDateTime.minusDays(2)

        val anyValidPurchaserId = 1

        val searchable = historyRepository.save(PurchaseHistory(purchaserId = anyValidPurchaserId,
                productId = 1, productName = "anyValidProductName", purchasedAt = anyDateTime))

        val notSearchable = historyRepository.save(PurchaseHistory(purchaserId = anyValidPurchaserId,
                productId = 1, productName = "anyValidProductName", purchasedAt = twoDayAgo))

        val histories = historyRepository.searchHistory(anyValidPurchaserId, aDayBefore, aDayAfter, Pageable.unpaged())

        assertTrue(histories.contains(searchable))
        assertFalse(histories.contains(notSearchable))
    }
}
