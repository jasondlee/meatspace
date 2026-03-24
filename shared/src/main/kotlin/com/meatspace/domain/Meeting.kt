package com.meatspace.domain

import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.LocalDate

@Serializable
data class Meeting(
    val id: Long? = null,
    val groupId: Long,
    val title: String,
    val description: String? = null,
    @Serializable(with = InstantSerializer::class)
    val startTime: Instant,
    @Serializable(with = InstantSerializer::class)
    val endTime: Instant,
    val isPublic: Boolean = true,
    val isOnline: Boolean = false,
    val location: String? = null,
    val onlineLink: String? = null,
    val recurrencePattern: String? = null,
    @Serializable(with = LocalDateSerializer::class)
    val recurrenceEndDate: LocalDate? = null,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant = Instant.now(),
    @Serializable(with = InstantSerializer::class)
    val updatedAt: Instant = Instant.now()
) {
    init {
        require(isOnline && onlineLink != null || !isOnline && location != null) {
            "Meeting must have either online link or location"
        }
    }
}
