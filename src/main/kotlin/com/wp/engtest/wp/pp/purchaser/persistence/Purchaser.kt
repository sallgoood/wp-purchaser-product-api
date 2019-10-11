package com.wp.engtest.wp.pp.purchaser.persistence

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import javax.persistence.*

@Entity
data class Purchaser(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,

        @Column(unique = true, nullable = false)
        val name: String,

        @Column(nullable = false, updatable = false)
        @CreatedDate
        val enrolledAt: LocalDateTime = now()
)
