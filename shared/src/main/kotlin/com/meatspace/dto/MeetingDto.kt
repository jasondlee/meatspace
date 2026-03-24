package com.meatspace.dto

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import kotlinx.serialization.Serializable

@Serializable
data class MeetingDto(
    val id: Long? = null,
    @field:NotNull(message = "Group ID is required")
    val groupId: Long,
    @field:NotBlank(message = "Title is required")
    val title: String,
    val description: String? = null,
    @field:NotBlank(message = "Start time is required")
    val startTime: String,
    @field:NotBlank(message = "End time is required")
    val endTime: String,
    val isPublic: Boolean = true,
    val isOnline: Boolean = false,
    val location: String? = null,
    val onlineLink: String? = null,
    val recurrencePattern: String? = null,
    val recurrenceEndDate: String? = null,
    val createdAt: String? = null
)
