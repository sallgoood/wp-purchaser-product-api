package com.wp.engtest.wp.pp.purchaser.history

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class PurchaseHistorySearchQueryResult(
        @JsonProperty("purchases")
        val histories: Map<LocalDate, List<PurchasedProductSummary>>
)
