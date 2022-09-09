package io.github.ufukhalis.productalertservice

import com.fasterxml.jackson.databind.DeserializationFeature
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProductAlertServiceApplication

fun main(args: Array<String>) {
    runApplication<ProductAlertServiceApplication>(*args)
}


val objectMapper = com.fasterxml.jackson.module.kotlin.jacksonObjectMapper()
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)!!
