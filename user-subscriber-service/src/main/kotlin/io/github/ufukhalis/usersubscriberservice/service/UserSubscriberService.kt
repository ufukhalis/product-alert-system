package io.github.ufukhalis.usersubscriberservice.service

import io.github.ufukhalis.usersubscriberservice.model.UserEntity
import io.github.ufukhalis.usersubscriberservice.model.UserSubscriberRequest
import io.github.ufukhalis.usersubscriberservice.model.UserSubscriberResponse
import io.github.ufukhalis.usersubscriberservice.repository.UserSubscriberRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserSubscriberService(
    private val repository: UserSubscriberRepository
) {

    private val logger = KotlinLogging.logger {}

    fun saveUser(userSubscriberRequest: UserSubscriberRequest): Mono<Void> {
        return repository.findByEmail(userSubscriberRequest.email).flatMap {
            logger.info { "User found with this email ${it.email} lowerThanPrice will be updated." }
            repository.save(it.copy(lowerThanPrice = userSubscriberRequest.lowerThanPrice, productId = userSubscriberRequest.productId))
        }.switchIfEmpty (
            saveNewUser(userSubscriberRequest)
        ).then()
    }

    private fun saveNewUser(request: UserSubscriberRequest): Mono<UserEntity> {
        logger.info { "User not found, will be created ${request.email}" }
        return repository.save(request.toEntity())
    }

    fun deleteUser(email: String): Mono<Void> {
        logger.info { "User will be deleted $email" }
        return repository.deleteByEmail(email)
    }

    fun findUsers(productId: String, currentPrice: Double): Flux<UserSubscriberResponse> {
        logger.info { "Find subscribed users $productId and $currentPrice" }
        return repository.findByProductIdAndLowerThanPriceIsGreaterThanEqual(productId, currentPrice)
            .map { UserSubscriberResponse.fromEntity(it) }
    }
}


