package com.wp.engtest.wp.pp.purchaser

import com.wp.engtest.wp.pp.purchaser.history.PurchaseHistorySearchQuery
import com.wp.engtest.wp.pp.purchaser.history.PurchaseHistorySearchQueryResult
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@RestController
class PurchaserController(
        val service: PurchaserService) {

    @PostMapping(path = ["/purchaser", "/purchasers"])
    fun enrollPurchaser(@Valid @RequestBody command: PurchaserEnrolmentCommand) {
        service.enrollPurchaser(command)
    }

    //NOTE alias with "/purchaser-product"
    @PostMapping(path = ["/purchasers/{id}/do-purchase"])
    fun purchaseProduct(@PathVariable id: Int,
                        @Valid @RequestBody command: PurchaseCommand) {
        service.purchase(id, command)
    }

    @GetMapping(path = [" /purchaser/{id}/product", "/purchasers/{id}/purchase-histories"])
    @ApiImplicitParams(
            ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "results page you want to retrieve (0..N)", defaultValue = "0"),
            ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "number of records per page.", defaultValue = "10"),
            ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "sorting criteria in the format: property(,asc|desc). " +
                            "default sort order is ascending. " +
                            "multiple sort criteria are supported."))
    fun searchPurchaseHistories(@PathVariable id: Int,
                                query: PurchaseHistorySearchQuery,
                                @PageableDefault(size = 10, sort = ["purchasedAt"])
                                @ApiIgnore pageable: Pageable): PurchaseHistorySearchQueryResult {
        return service.findPurchaseHistories(id, query, pageable)
    }
}
