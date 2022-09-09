package io.github.ufukhalis.usersubscriberservice.controller

import io.github.ufukhalis.usersubscriberservice.model.UserSubscriberRequest
import io.github.ufukhalis.usersubscriberservice.model.UserSubscriberResponse
import io.github.ufukhalis.usersubscriberservice.service.UserSubscriberService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class UserSubscriberController(
    private val userSubscriberService: UserSubscriberService
) {


    @PutMapping("/v1/user/subscriber")
    fun saveUser(@RequestBody userSubscriberRequest: UserSubscriberRequest): Mono<Void> {
        return userSubscriberService.saveUser(userSubscriberRequest)
    }

    @DeleteMapping("/v1/user/subscriber/{email}")
    fun deleteUser(@PathVariable("email") email: String): Mono<Void> {
        return userSubscriberService.deleteUser(email)
    }

    @GetMapping("/v1/user/subscriber")
    fun findUsers(@RequestParam("productId") productId: String,
                  @RequestParam("currentPrice") currentPrice: Double): Flux<UserSubscriberResponse> {
        return userSubscriberService.findUsers(productId, currentPrice)
    }
}
