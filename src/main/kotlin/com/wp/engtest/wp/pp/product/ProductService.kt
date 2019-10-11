package com.wp.engtest.wp.pp.product

import com.wp.engtest.wp.pp.product.persistence.Product
import com.wp.engtest.wp.pp.product.persistence.ProductRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService(
        val repository: ProductRepository) {

    fun enrollProduct(command: ProductEnrolmentCommand) {
        val (name) = command
        val purchaser = Product(name = name)
        repository.save(purchaser)
    }

    fun findProduct(id: Int): Optional<Product> {
        return repository.findById(id)
    }
}
