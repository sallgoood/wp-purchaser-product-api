package com.wp.engtest.wp.pp.purchaser.history.persistence

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import javax.persistence.*

@Entity
data class PurchaseHistory(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,

        @Column(nullable = false)
        val purchaserId: Int,

        @Column(nullable = false)
        val productId: Int,

        @Column(nullable = false)
        val productName: String,

        @Column(nullable = false, updatable = false)
        @CreatedDate
        val purchasedAt: LocalDateTime = now()
)
