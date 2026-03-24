package io.meatspace.shared.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.DayOfWeek
import java.time.Instant

enum class Visibility {
    PUBLIC,
    PRIVATE,
}

enum class MeetingMode {
    IN_PERSON,
    ONLINE,
    HYBRID,
}

enum class UserRole {
    MEMBER,
    ORGANIZER,
}

enum class RecurrenceFrequency {
    DAILY,
    WEEKLY,
    MONTHLY,
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class RecurrencePattern @JsonCreator constructor(
    @param:JsonProperty("frequency") val frequency: RecurrenceFrequency,
    @param:JsonProperty("interval") val interval: Int = 1,
    @param:JsonProperty("byDay") val byDay: Set<DayOfWeek> = emptySet(),
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class GroupSummary @JsonCreator constructor(
    @param:JsonProperty("id") val id: Long,
    @param:JsonProperty("name") val name: String,
    @param:JsonProperty("visibility") val visibility: Visibility,
    @param:JsonProperty("description") val description: String?,
    @param:JsonProperty("location") val location: String?,
    @param:JsonProperty("website") val website: String?,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class MeetingSummary @JsonCreator constructor(
    @param:JsonProperty("id") val id: Long,
    @param:JsonProperty("groupId") val groupId: Long,
    @param:JsonProperty("title") val title: String,
    @param:JsonProperty("startsAt") val startsAt: Instant,
    @param:JsonProperty("endsAt") val endsAt: Instant?,
    @param:JsonProperty("mode") val mode: MeetingMode,
    @param:JsonProperty("visibility") val visibility: Visibility,
    @param:JsonProperty("location") val location: String?,
    @param:JsonProperty("meetingUrl") val meetingUrl: String?,
    @param:JsonProperty("recurrence") val recurrence: RecurrencePattern?,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class RegistrationRequest @JsonCreator constructor(
    @param:JsonProperty("username") val username: String,
    @param:JsonProperty("password") val password: String,
    @param:JsonProperty("organizer") val organizer: Boolean = false,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class LoginRequest @JsonCreator constructor(
    @param:JsonProperty("username") val username: String,
    @param:JsonProperty("password") val password: String,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TokenResponse @JsonCreator constructor(
    @param:JsonProperty("accessToken") val accessToken: String,
    @param:JsonProperty("tokenType") val tokenType: String = "Bearer",
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CreateGroupRequest @JsonCreator constructor(
    @param:JsonProperty("name") val name: String,
    @param:JsonProperty("visibility") val visibility: Visibility = Visibility.PUBLIC,
    @param:JsonProperty("description") val description: String? = null,
    @param:JsonProperty("logoUrl") val logoUrl: String? = null,
    @param:JsonProperty("website") val website: String? = null,
    @param:JsonProperty("location") val location: String? = null,
    @param:JsonProperty("contactEmail") val contactEmail: String? = null,
    @param:JsonProperty("contactPhone") val contactPhone: String? = null,
    @param:JsonProperty("contactAddress") val contactAddress: String? = null,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CreateMeetingRequest @JsonCreator constructor(
    @param:JsonProperty("groupId") val groupId: Long,
    @param:JsonProperty("title") val title: String,
    @param:JsonProperty("description") val description: String? = null,
    @param:JsonProperty("startsAt") val startsAt: Instant,
    @param:JsonProperty("endsAt") val endsAt: Instant? = null,
    @param:JsonProperty("mode") val mode: MeetingMode,
    @param:JsonProperty("visibility") val visibility: Visibility = Visibility.PUBLIC,
    @param:JsonProperty("location") val location: String? = null,
    @param:JsonProperty("meetingUrl") val meetingUrl: String? = null,
    @param:JsonProperty("recurrence") val recurrence: RecurrencePattern? = null,
)
