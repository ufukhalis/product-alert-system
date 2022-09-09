package io.github.ufukhalis.productalertservice.model

data class ProductMessage(val productId: String, val productTitle: String, val price: Double)

data class ProductResponse(val email: String, val productId: String, val lowerThanPrice: Double)
