package io.meatspace.shared.validation

import io.meatspace.shared.model.CreateMeetingRequest
import io.meatspace.shared.model.MeetingMode
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ValidationTest {
    @Test
    fun `online meetings require a link`() {
        val request = CreateMeetingRequest(
            groupId = 1L,
            title = "Remote planning",
            startsAt = Instant.parse("2026-03-24T18:00:00Z"),
            mode = MeetingMode.ONLINE,
        )

        assertFailsWith<IllegalArgumentException> {
            request.validate()
        }
    }
}
