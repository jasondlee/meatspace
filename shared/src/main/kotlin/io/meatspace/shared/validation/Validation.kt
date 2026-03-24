package io.meatspace.shared.validation

import io.meatspace.shared.model.CreateGroupRequest
import io.meatspace.shared.model.CreateMeetingRequest
import io.meatspace.shared.model.MeetingMode

fun CreateGroupRequest.validate() {
    require(name.isNotBlank()) { "Group name is required." }
}

fun CreateMeetingRequest.validate() {
    require(title.isNotBlank()) { "Meeting title is required." }
    require(endsAt == null || !endsAt.isBefore(startsAt)) { "Meeting end time must be after start time." }
    require(mode != MeetingMode.IN_PERSON || !location.isNullOrBlank()) { "In-person meetings require a location." }
    require(mode != MeetingMode.ONLINE || !meetingUrl.isNullOrBlank()) { "Online meetings require a link." }
    require(mode != MeetingMode.HYBRID || (!location.isNullOrBlank() && !meetingUrl.isNullOrBlank())) {
        "Hybrid meetings require both a location and a link."
    }
}
