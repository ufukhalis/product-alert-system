package io.github.ufukhalis.productparserjob.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderOptions





@Configuration
class KafkaConfig(
    @Value("\${kafka.bootstrapServers}")
    private val bootstrapServers: String,
) {

    @Bean("kafkaProducer")
    fun kafkaProducer(): KafkaSender<String, String> {
        val props: MutableMap<String, Any> = HashMap()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        props[ProducerConfig.CLIENT_ID_CONFIG] = "product-parser-producer"
        props[ProducerConfig.ACKS_CONFIG] = "all"
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java

        val senderOptions: SenderOptions<String, String> = SenderOptions.create(props)

        return KafkaSender.create(senderOptions)
    }
}
