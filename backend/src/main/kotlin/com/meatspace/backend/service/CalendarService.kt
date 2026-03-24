package com.meatspace.backend.service

import com.meatspace.backend.entity.MeetingEntity
import com.meatspace.backend.repository.MeetingRepository
import jakarta.enterprise.context.ApplicationScoped
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@ApplicationScoped
class CalendarService(private val meetingRepository: MeetingRepository) {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
        .withZone(ZoneId.of("UTC"))

    fun generateICalForGroup(groupId: Long): String {
        val meetings = meetingRepository.findByGroupId(groupId)
        return generateICal(meetings, "Group $groupId Calendar")
    }

    private fun generateICal(meetings: List<MeetingEntity>, calendarName: String): String {
        val ical = StringBuilder()

        ical.append("BEGIN:VCALENDAR\r\n")
        ical.append("VERSION:2.0\r\n")
        ical.append("PRODID:-//Meatspace//Calendar//EN\r\n")
        ical.append("CALSCALE:GREGORIAN\r\n")
        ical.append("METHOD:PUBLISH\r\n")
        ical.append("X-WR-CALNAME:$calendarName\r\n")
        ical.append("X-WR-TIMEZONE:UTC\r\n")

        meetings.forEach { meeting ->
            ical.append("BEGIN:VEVENT\r\n")
            ical.append("UID:${meeting.id}@meatspace.com\r\n")
            ical.append("DTSTAMP:${dateFormatter.format(meeting.createdAt)}\r\n")
            ical.append("DTSTART:${dateFormatter.format(meeting.startTime)}\r\n")
            ical.append("DTEND:${dateFormatter.format(meeting.endTime)}\r\n")
            ical.append("SUMMARY:${escapeText(meeting.title)}\r\n")

            meeting.description?.let {
                ical.append("DESCRIPTION:${escapeText(it)}\r\n")
            }

            if (meeting.isOnline && meeting.onlineLink != null) {
                ical.append("LOCATION:Online\r\n")
                ical.append("URL:${meeting.onlineLink}\r\n")
            } else if (meeting.location != null) {
                ical.append("LOCATION:${escapeText(meeting.location!!)}\r\n")
            }

            ical.append("STATUS:CONFIRMED\r\n")
            ical.append("END:VEVENT\r\n")
        }

        ical.append("END:VCALENDAR\r\n")

        return ical.toString()
    }

    private fun escapeText(text: String): String {
        return text
            .replace("\\", "\\\\")
            .replace(";", "\\;")
            .replace(",", "\\,")
            .replace("\n", "\\n")
    }
}
