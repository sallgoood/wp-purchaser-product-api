package com.wp.engtest.wp.pp.product.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Int> {

    fun findByName(name: String): Product
}
