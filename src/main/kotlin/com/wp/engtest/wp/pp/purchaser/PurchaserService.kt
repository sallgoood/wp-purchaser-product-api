package com.wp.engtest.wp.pp.purchaser

import com.wp.engtest.wp.pp.product.ProductService
import com.wp.engtest.wp.pp.product.persistence.ProductRepository
import com.wp.engtest.wp.pp.purchaser.history.*
import com.wp.engtest.wp.pp.purchaser.history.persistence.PurchaseHistory
import com.wp.engtest.wp.pp.purchaser.history.persistence.PurchaseHistoryRepository
import com.wp.engtest.wp.pp.purchaser.persistence.Purchaser
import com.wp.engtest.wp.pp.purchaser.persistence.PurchaserRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate
import javax.persistence.EntityNotFoundException

@Service
class PurchaserService(
        val purchaserRepository: PurchaserRepository,
        val productService: ProductService,
        val historyRepository: PurchaseHistoryRepository) {

    fun enrollPurchaser(command: PurchaserEnrolmentCommand) {
        val (name) = command

        val purchaser = Purchaser(name = name)
        purchaserRepository.save(purchaser)
    }

    fun purchase(id: Int, command: PurchaseCommand) {
        val (productId) = command

        val (_, name, _) = productService.findProduct(productId)
                .orElseThrow { EntityNotFoundException("$productId not found") }

        val history = PurchaseHistory(purchaserId = id, productId = productId, productName = name)
        historyRepository.save(history)
    }

    fun findPurchaseHistories(id: Int, query: PurchaseHistorySearchQuery, pageable: Pageable): PurchaseHistorySearchQueryResult {
        val (startDateTime, endDateTime) = query

        val historiesByDate = historyRepository.searchHistory(id, startDateTime, endDateTime, pageable)
                .groupBy(keySelector = { it.purchasedAt.toLocalDate() },
                        valueTransform = { PurchasedProductSummary(it.productName) })
                .toSortedMap(compareByDescending { it })

        return PurchaseHistorySearchQueryResult(historiesByDate)
    }
}
