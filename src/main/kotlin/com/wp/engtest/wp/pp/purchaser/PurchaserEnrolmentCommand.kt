package com.wp.engtest.wp.pp.purchaser

import javax.validation.constraints.NotBlank

data class PurchaserEnrolmentCommand(
        @field:NotBlank
        val name: String
)
