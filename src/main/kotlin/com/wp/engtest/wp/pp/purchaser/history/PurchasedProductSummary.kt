package com.wp.engtest.wp.pp.purchaser.history

import com.fasterxml.jackson.annotation.JsonProperty

data class PurchasedProductSummary(
        @JsonProperty("product")
        val productName: String
)
