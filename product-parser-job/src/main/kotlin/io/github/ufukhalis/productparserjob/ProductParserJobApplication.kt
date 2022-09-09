package io.github.ufukhalis.productparserjob

import com.fasterxml.jackson.databind.DeserializationFeature
import io.github.ufukhalis.productparserjob.service.ProductParserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProductParserJobApplication(
    private val productParserService: ProductParserService
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        productParserService.start()
    }
}


fun main(args: Array<String>) {
    runApplication<ProductParserJobApplication>(*args)
}

val objectMapper = com.fasterxml.jackson.module.kotlin.jacksonObjectMapper()
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)!!
