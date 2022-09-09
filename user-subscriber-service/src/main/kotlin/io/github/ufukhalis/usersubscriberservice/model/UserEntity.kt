package io.github.ufukhalis.usersubscriberservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class UserEntity (
    @Id val id: String?,
    val productId: String,
    @Indexed val email: String,
    val lowerThanPrice: Double
)
