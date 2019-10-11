package com.wp.engtest.wp.pp.product.persistence

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import javax.persistence.*

@Entity
data class Product(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,

        @Column(unique = true, nullable = false)
        val name: String,

        @Column(nullable = false, updatable = false)
        @CreatedDate
        val enrolledAt: LocalDateTime = now()
)
