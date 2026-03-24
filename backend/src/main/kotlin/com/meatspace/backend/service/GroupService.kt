package com.meatspace.backend.service

import com.meatspace.backend.entity.GroupEntity
import com.meatspace.backend.repository.GroupRepository
import com.meatspace.backend.repository.UserRepository
import com.meatspace.dto.GroupDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.ForbiddenException
import jakarta.ws.rs.NotFoundException
import java.time.Instant

@ApplicationScoped
class GroupService(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
) {

    fun findAll(): List<GroupDto> {
        return groupRepository.listAll().map { it.toDto() }
    }

    fun findPublicGroups(): List<GroupDto> {
        return groupRepository.findPublicGroups().map { it.toDto() }
    }

    fun findById(id: Long): GroupDto {
        val group = groupRepository.findById(id)
            ?: throw NotFoundException("Group not found")
        return group.toDto()
    }

    fun findByOrganizerId(userId: Long): List<GroupDto> {
        return groupRepository.findByOrganizerId(userId).map { it.toDto() }
    }

    @Transactional
    fun createGroup(dto: GroupDto, organizerId: Long): GroupDto {
        val organizer = userRepository.findById(organizerId)
            ?: throw NotFoundException("Organizer not found")

        val group = GroupEntity().apply {
            name = dto.name
            description = dto.description
            isPublic = dto.isPublic
            logoUrl = dto.logoUrl
            website = dto.website
            location = dto.location
            contactEmail = dto.contactEmail
            contactPhone = dto.contactPhone
            contactAddress = dto.contactAddress
            createdAt = Instant.now()
            updatedAt = Instant.now()
            organizers.add(organizer)
        }

        groupRepository.persist(group)
        return group.toDto()
    }

    @Transactional
    fun updateGroup(id: Long, dto: GroupDto, userId: Long): GroupDto {
        val group = groupRepository.findById(id)
            ?: throw NotFoundException("Group not found")

        if (!groupRepository.isUserOrganizer(id, userId)) {
            throw ForbiddenException("Only organizers can update the group")
        }

        group.apply {
            name = dto.name
            description = dto.description
            isPublic = dto.isPublic
            logoUrl = dto.logoUrl
            website = dto.website
            location = dto.location
            contactEmail = dto.contactEmail
            contactPhone = dto.contactPhone
            contactAddress = dto.contactAddress
            updatedAt = Instant.now()
        }

        groupRepository.persist(group)
        return group.toDto()
    }

    @Transactional
    fun deleteGroup(id: Long, userId: Long) {
        val group = groupRepository.findById(id)
            ?: throw NotFoundException("Group not found")

        if (!groupRepository.isUserOrganizer(id, userId)) {
            throw ForbiddenException("Only organizers can delete the group")
        }

        groupRepository.delete(group)
    }

    @Transactional
    fun addOrganizer(groupId: Long, userIdToAdd: Long, requestingUserId: Long) {
        val group = groupRepository.findById(groupId)
            ?: throw NotFoundException("Group not found")

        if (!groupRepository.isUserOrganizer(groupId, requestingUserId)) {
            throw ForbiddenException("Only organizers can add new organizers")
        }

        val userToAdd = userRepository.findById(userIdToAdd)
            ?: throw NotFoundException("User to add not found")

        group.organizers.add(userToAdd)
        groupRepository.persist(group)
    }

    private fun GroupEntity.toDto() = GroupDto(
        id = id,
        name = name,
        description = description,
        isPublic = isPublic,
        logoUrl = logoUrl,
        website = website,
        location = location,
        contactEmail = contactEmail,
        contactPhone = contactPhone,
        contactAddress = contactAddress,
        organizerIds = organizers.map { it.id!! },
        createdAt = createdAt.toString()
    )
}
