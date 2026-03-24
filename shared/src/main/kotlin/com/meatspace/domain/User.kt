package com.meatspace.domain

import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class User(
    val id: Long? = null,
    val username: String,
    val email: String,
    val passwordHash: String,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant = Instant.now(),
    @Serializable(with = InstantSerializer::class)
    val updatedAt: Instant = Instant.now()
)
