package com.meatspace.domain

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Recurrence(
    val pattern: String,
    @Serializable(with = LocalDateSerializer::class)
    val endDate: LocalDate? = null
)

enum class RecurrencePattern(val value: String) {
    DAILY("DAILY"),
    WEEKLY("WEEKLY"),
    BIWEEKLY("BIWEEKLY"),
    MONTHLY("MONTHLY"),
    YEARLY("YEARLY")
}
