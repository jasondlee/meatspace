package io.meatspace.backend.service

import io.meatspace.backend.domain.MeetingEntity
import io.meatspace.backend.repository.GroupRepository
import io.meatspace.backend.repository.MeetingRepository
import io.meatspace.shared.model.CreateMeetingRequest
import io.meatspace.shared.model.MeetingSummary
import io.meatspace.shared.validation.validate
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException

@ApplicationScoped
class MeetingService(
    private val groupRepository: GroupRepository,
    private val meetingRepository: MeetingRepository,
) {
    fun listPublic(): List<MeetingSummary> = meetingRepository.listPublicMeetings().map { it.toSummary() }

    @Transactional
    fun create(request: CreateMeetingRequest): MeetingSummary {
        request.validate()
        val group = groupRepository.findById(request.groupId) ?: throw NotFoundException("Group not found.")
        val entity = MeetingEntity().apply {
            this.group = group
            title = request.title.trim()
            description = request.description?.trim()?.takeIf { it.isNotBlank() }
            startsAt = request.startsAt
            endsAt = request.endsAt
            mode = request.mode
            visibility = request.visibility
            location = request.location?.trim()?.takeIf { it.isNotBlank() }
            meetingUrl = request.meetingUrl?.trim()?.takeIf { it.isNotBlank() }
            recurrenceRule = request.recurrence?.let { "${it.frequency}:${it.interval}:${it.byDay.joinToString(",")}" }
        }
        meetingRepository.persist(entity)
        return entity.toSummary()
    }

    private fun MeetingEntity.toSummary(): MeetingSummary =
        MeetingSummary(
            id = requireNotNull(id),
            groupId = requireNotNull(group.id),
            title = title,
            startsAt = startsAt,
            endsAt = endsAt,
            mode = mode,
            visibility = visibility,
            location = location,
            meetingUrl = meetingUrl,
            recurrence = null,
        )
}
