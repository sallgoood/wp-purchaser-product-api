package com.wp.engtest.wp.pp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
class WpPurchaserProductApiApplication

fun main(args: Array<String>) {
	runApplication<WpPurchaserProductApiApplication>(*args)
}
