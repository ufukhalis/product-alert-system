package io.github.ufukhalis.productalertservice.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.kafka.receiver.ReceiverOptions


@Configuration
class KafkaConfig(
    @Value("\${kafka.bootstrapServers}")
    private val bootstrapServers: String,
) {

    @Bean
    fun kafkaConsumer(): ReceiverOptions<String, String> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        props[ConsumerConfig.CLIENT_ID_CONFIG] = "product-alert-consumer"
        props[ConsumerConfig.GROUP_ID_CONFIG] = "product-alert-group"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"

        return ReceiverOptions.create(props)
    }
}
