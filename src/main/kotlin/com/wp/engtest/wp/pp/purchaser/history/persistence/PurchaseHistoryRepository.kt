package com.wp.engtest.wp.pp.purchaser.history.persistence

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface PurchaseHistoryRepository : JpaRepository<PurchaseHistory, Int> {

    @Query("""SELECT h from PurchaseHistory h
            where h.purchaserId = :purchaserId
            and h.purchasedAt >= :startDateTime 
            and h.purchasedAt < :endDateTime""")
    fun searchHistory(@Param("purchaserId") purchaserId: Int,
                      @Param("startDateTime") startDateTime: LocalDateTime,
                      @Param("endDateTime") endDateTime: LocalDateTime,
                      pageable: Pageable): List<PurchaseHistory>

    fun findByPurchaserId(purchaserId: Int): List<PurchaseHistory>
}
