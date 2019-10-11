package com.wp.engtest.wp.pp.purchaser

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class PurchaseCommand(
        @field:NotNull
        @field:Min(1)
        val productId: Int
)
