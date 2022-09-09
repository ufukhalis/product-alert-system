package io.github.ufukhalis.productparserjob.repository

import io.github.ufukhalis.productparserjob.model.ProductEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : ReactiveMongoRepository<ProductEntity, String>
