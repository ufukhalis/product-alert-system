package io.github.ufukhalis.productparserjob.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class ProductEntity (
    @Id val id: String,
    val name: String,
)
