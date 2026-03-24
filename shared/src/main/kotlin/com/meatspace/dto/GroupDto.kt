package com.meatspace.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import kotlinx.serialization.Serializable

@Serializable
data class GroupDto(
    val id: Long? = null,
    @field:NotBlank(message = "Group name is required")
    @field:Size(min = 3, max = 255, message = "Group name must be between 3 and 255 characters")
    val name: String,
    val description: String? = null,
    val isPublic: Boolean = true,
    val logoUrl: String? = null,
    val website: String? = null,
    val location: String? = null,
    val contactEmail: String? = null,
    val contactPhone: String? = null,
    val contactAddress: String? = null,
    val organizerIds: List<Long>? = null,
    val createdAt: String? = null
)
