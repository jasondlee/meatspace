package com.meatspace.backend.service

import com.meatspace.backend.entity.MeetingEntity
import com.meatspace.backend.repository.GroupRepository
import com.meatspace.backend.repository.MeetingRepository
import com.meatspace.dto.MeetingDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.ForbiddenException
import jakarta.ws.rs.NotFoundException
import java.time.Instant
import java.time.LocalDate

@ApplicationScoped
class MeetingService(
    private val meetingRepository: MeetingRepository,
    private val groupRepository: GroupRepository
) {

    fun findAll(): List<MeetingDto> {
        return meetingRepository.listAll().map { it.toDto() }
    }

    fun findPublicMeetings(): List<MeetingDto> {
        return meetingRepository.findPublicMeetings().map { it.toDto() }
    }

    fun findUpcomingPublicMeetings(): List<MeetingDto> {
        return meetingRepository.findUpcomingPublicMeetings().map { it.toDto() }
    }

    fun findById(id: Long): MeetingDto {
        val meeting = meetingRepository.findById(id)
            ?: throw NotFoundException("Meeting not found")
        return meeting.toDto()
    }

    fun findByGroupId(groupId: Long): List<MeetingDto> {
        return meetingRepository.findByGroupId(groupId).map { it.toDto() }
    }

    @Transactional
    fun createMeeting(dto: MeetingDto, userId: Long): MeetingDto {
        val group = groupRepository.findById(dto.groupId)
            ?: throw NotFoundException("Group not found")

        if (!groupRepository.isUserOrganizer(dto.groupId, userId)) {
            throw ForbiddenException("Only organizers can create meetings")
        }

        val meeting = MeetingEntity().apply {
            this.group = group
            title = dto.title
            description = dto.description
            startTime = Instant.parse(dto.startTime)
            endTime = Instant.parse(dto.endTime)
            isPublic = dto.isPublic
            isOnline = dto.isOnline
            location = dto.location
            onlineLink = dto.onlineLink
            recurrencePattern = dto.recurrencePattern
            recurrenceEndDate = dto.recurrenceEndDate?.let { LocalDate.parse(it) }
            createdAt = Instant.now()
            updatedAt = Instant.now()
        }

        meetingRepository.persist(meeting)
        return meeting.toDto()
    }

    @Transactional
    fun updateMeeting(id: Long, dto: MeetingDto, userId: Long): MeetingDto {
        val meeting = meetingRepository.findById(id)
            ?: throw NotFoundException("Meeting not found")

        val groupId = meeting.group?.id
            ?: throw IllegalStateException("Meeting has no associated group")

        if (!groupRepository.isUserOrganizer(groupId, userId)) {
            throw ForbiddenException("Only organizers can update meetings")
        }

        meeting.apply {
            title = dto.title
            description = dto.description
            startTime = Instant.parse(dto.startTime)
            endTime = Instant.parse(dto.endTime)
            isPublic = dto.isPublic
            isOnline = dto.isOnline
            location = dto.location
            onlineLink = dto.onlineLink
            recurrencePattern = dto.recurrencePattern
            recurrenceEndDate = dto.recurrenceEndDate?.let { LocalDate.parse(it) }
            updatedAt = Instant.now()
        }

        meetingRepository.persist(meeting)
        return meeting.toDto()
    }

    @Transactional
    fun deleteMeeting(id: Long, userId: Long) {
        val meeting = meetingRepository.findById(id)
            ?: throw NotFoundException("Meeting not found")

        val groupId = meeting.group?.id
            ?: throw IllegalStateException("Meeting has no associated group")

        if (!groupRepository.isUserOrganizer(groupId, userId)) {
            throw ForbiddenException("Only organizers can delete meetings")
        }

        meetingRepository.delete(meeting)
    }

    private fun MeetingEntity.toDto() = MeetingDto(
        id = id,
        groupId = group?.id ?: 0,
        title = title,
        description = description,
        startTime = startTime.toString(),
        endTime = endTime.toString(),
        isPublic = isPublic,
        isOnline = isOnline,
        location = location,
        onlineLink = onlineLink,
        recurrencePattern = recurrencePattern,
        recurrenceEndDate = recurrenceEndDate?.toString(),
        createdAt = createdAt.toString()
    )
}
