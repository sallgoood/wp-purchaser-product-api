package com.wp.engtest.wp.pp.purchaser.history

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME
import java.time.LocalDate
import java.time.LocalDate.of
import java.time.LocalDateTime
import java.time.LocalDateTime.now

data class PurchaseHistorySearchQuery(

        @DateTimeFormat(iso = DATE_TIME)
        val startDateTime: LocalDateTime = of(2018, 1, 1).atStartOfDay(),

        @DateTimeFormat(iso = DATE_TIME)
        val endDateTime: LocalDateTime = now()
)
