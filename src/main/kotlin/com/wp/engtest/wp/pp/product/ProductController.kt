package com.wp.engtest.wp.pp.product

import com.wp.engtest.wp.pp.purchaser.PurchaserEnrolmentCommand
import com.wp.engtest.wp.pp.purchaser.PurchaserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class ProductController(
        val service: ProductService) {

    @PostMapping(path = ["/product", "/products"])
    fun enrollProduct(@Valid @RequestBody command: ProductEnrolmentCommand) {
        service.enrollProduct(command)
    }
}
