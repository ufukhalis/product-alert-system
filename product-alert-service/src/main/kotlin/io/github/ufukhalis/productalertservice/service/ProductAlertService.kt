package io.github.ufukhalis.productalertservice.service

import com.fasterxml.jackson.module.kotlin.readValue
import io.github.ufukhalis.productalertservice.model.ProductMessage
import io.github.ufukhalis.productalertservice.objectMapper
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverOptions
import reactor.kafka.receiver.ReceiverRecord
import javax.annotation.PostConstruct

@Service
class ProductAlertService(
    private val kafkaConsumer: ReceiverOptions<String, String>,
    private val userSubscriberService: UserSubscriberService,
    @Value("\${kafka.topic}")
    private val topic: String,
) {

    private val logger = KotlinLogging.logger {}

    @PostConstruct
    fun start() {
        kafkaReceiver()
            .map {
                objectMapper.readValue<ProductMessage>(it.value()) to it
            }.flatMapSequential { pair ->
                val productMessage = pair.first
                userSubscriberService.call(productId = productMessage.productId, currentPrice = productMessage.price)
                    .doFinally { pair.second.receiverOffset().acknowledge() }
            }.map { productResponse ->
                logger.info { "Sending email for ${productResponse.email} product id ${productResponse.productId} price ${productResponse.lowerThanPrice}" }
            }.subscribe()
    }

    private fun kafkaReceiver(): Flux<ReceiverRecord<String, String>> {
        val options = kafkaConsumer.subscription(listOf(topic))
            .addAssignListener {
                logger.info { "Partitions are assigned $it" }
            }
            .addRevokeListener {
                logger.info { "Partitions are revoked $it" }
            }

        return KafkaReceiver.create(options).receive()
    }
}
