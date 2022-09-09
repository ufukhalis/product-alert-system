package io.github.ufukhalis.usersubscriberservice.model

data class UserSubscriberRequest(val email: String, val productId: String, val lowerThanPrice: Double) {
    fun toEntity(): UserEntity {
        return UserEntity(
            id = null,
            productId = this.productId,
            email = this.email,
            lowerThanPrice = this.lowerThanPrice
        )
    }
}

data class UserSubscriberResponse(val email: String, val productId: String, val lowerThanPrice: Double) {
    companion object {
        fun fromEntity(userEntity: UserEntity): UserSubscriberResponse {
            return UserSubscriberResponse(
                email = userEntity.email,
                productId = userEntity.productId,
                lowerThanPrice = userEntity.lowerThanPrice
            )
        }
    }
}
