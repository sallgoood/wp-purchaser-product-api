package com.wp.engtest.wp.pp.purchaser.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface PurchaserRepository : JpaRepository<Purchaser, Int> {

    fun findByName(name: String): Purchaser
}
