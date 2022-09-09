package io.github.ufukhalis.productparserjob.service

import io.github.ufukhalis.productparserjob.model.ProductMessage
import io.github.ufukhalis.productparserjob.objectMapper
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderRecord

@Service
class KafkaProducerService(
    @Value("\${kafka.topic}")
    private val topic: String,
    private val producer: KafkaSender<String, String>,
) {


    fun produce(productMessage: ProductMessage): Mono<ProductMessage> {
        val message = objectMapper.writeValueAsString(productMessage)
        val record = SenderRecord.create(ProducerRecord(topic, productMessage.productId, message), productMessage.productId)

        return producer.send(Flux.just(record))
            .then()
            .thenReturn(productMessage)
    }

}
