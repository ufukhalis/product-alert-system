package io.github.ufukhalis.productparserjob.model

data class ProductMessage(val productId: String, val productTitle: String, val price: Double) {
    companion object {
        fun fromEntity(productEntity: ProductEntity, price: Double): ProductMessage {
            return ProductMessage(
                productId = productEntity.id,
                productTitle = productEntity.name,
                price = price
            )
        }
    }
}
