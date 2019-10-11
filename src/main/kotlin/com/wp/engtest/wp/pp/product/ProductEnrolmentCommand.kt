package com.wp.engtest.wp.pp.product

import javax.validation.constraints.NotBlank

data class ProductEnrolmentCommand(
        @field:NotBlank
        val name: String
)
