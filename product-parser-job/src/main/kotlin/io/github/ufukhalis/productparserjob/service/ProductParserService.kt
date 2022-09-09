package io.github.ufukhalis.productparserjob.service

import io.github.ufukhalis.productparserjob.model.ProductEntity
import io.github.ufukhalis.productparserjob.model.ProductMessage
import io.github.ufukhalis.productparserjob.repository.ProductRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import kotlin.random.Random
import kotlin.system.exitProcess

@Service
class ProductParserService(
    private val repository: ProductRepository,
    private val kafkaProducerService: KafkaProducerService
) {

    private val logger = KotlinLogging.logger {}

    private val randomPrice = Random(100)

    fun start() {
        Flux.range(1, 100)
            .flatMap {
                val entity = ProductEntity("product$it", "product-name-$it")
                repository.save(entity)
                    .flatMap {productEntity ->
                        kafkaProducerService.produce(ProductMessage.fromEntity(productEntity, randomPrice.nextDouble(10.0, 100.0)))
                    }.doOnSuccess {
                        logger.info { "Product saved and sent to kafka" }
                    }.doOnError {
                        logger.error { "Error occurred $it" }
                    }
            }.doFinally {
                exitProcess(0)
            }
            .subscribe()

    }
}

