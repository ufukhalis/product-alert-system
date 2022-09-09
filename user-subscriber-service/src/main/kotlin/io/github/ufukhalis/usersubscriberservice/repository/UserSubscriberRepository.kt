package io.github.ufukhalis.usersubscriberservice.repository

import io.github.ufukhalis.usersubscriberservice.model.UserEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface UserSubscriberRepository : ReactiveMongoRepository<UserEntity, String> {
    fun findByEmail(email: String): Mono<UserEntity>

    fun deleteByEmail(email: String): Mono<Void>

    fun findByProductIdAndLowerThanPriceIsGreaterThanEqual(productId: String, lowerThanPrice: Double): Flux<UserEntity>
}
