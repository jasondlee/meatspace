package com.meatspace.domain

import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class Group(
    val id: Long? = null,
    val name: String,
    val description: String? = null,
    val isPublic: Boolean = true,
    val logoUrl: String? = null,
    val website: String? = null,
    val location: String? = null,
    val contactEmail: String? = null,
    val contactPhone: String? = null,
    val contactAddress: String? = null,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant = Instant.now(),
    @Serializable(with = InstantSerializer::class)
    val updatedAt: Instant = Instant.now()
)
