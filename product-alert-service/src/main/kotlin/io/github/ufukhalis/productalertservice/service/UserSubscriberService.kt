package io.github.ufukhalis.productalertservice.service

import io.github.ufukhalis.productalertservice.model.ProductResponse
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Service
class UserSubscriberService(
    @Value("\${userSubscriberService.host}")
    private val host: String,
) {

    private val logger = KotlinLogging.logger {}

    private val webClient = WebClient.builder()
        .clientConnector(ReactorClientHttpConnector(
            HttpClient.create()
                .responseTimeout(Duration.ofSeconds(5))
        ))
        .build()

    fun call(productId: String, currentPrice: Double): Flux<ProductResponse> {
        return webClient.get()
            .uri("$host/v1/user/subscriber?productId=$productId&currentPrice=$currentPrice")
            .retrieve()
            .bodyToFlux(ProductResponse::class.java)
            .onErrorResume {
                logger.error { "user-subscriber-service is not reachable $it" }
                Flux.empty()
            }
    }
}
