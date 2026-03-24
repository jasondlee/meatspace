package io.meatspace.backend.service

import io.meatspace.backend.domain.GroupEntity
import io.meatspace.backend.repository.GroupRepository
import io.meatspace.backend.repository.UserAccountRepository
import io.meatspace.shared.model.CreateGroupRequest
import io.meatspace.shared.model.GroupSummary
import io.meatspace.shared.validation.validate
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.NotFoundException

@ApplicationScoped
class GroupService(
    private val groupRepository: GroupRepository,
    private val userAccountRepository: UserAccountRepository,
) {
    fun listPublic(): List<GroupSummary> = groupRepository.listPublicGroups().map { it.toSummary() }

    @Transactional
    fun create(request: CreateGroupRequest, organizerUsername: String): GroupSummary {
        request.validate()
        val organizer = userAccountRepository.findByUsername(organizerUsername)
            ?: throw NotFoundException("Organizer not found.")
        if (organizer.role.name != "ORGANIZER") {
            throw BadRequestException("Only organizer accounts can create groups.")
        }

        val entity = GroupEntity().apply {
            name = request.name.trim()
            visibility = request.visibility
            description = request.description?.trim()?.takeIf { it.isNotBlank() }
            logoUrl = request.logoUrl?.trim()?.takeIf { it.isNotBlank() }
            website = request.website?.trim()?.takeIf { it.isNotBlank() }
            location = request.location?.trim()?.takeIf { it.isNotBlank() }
            contactEmail = request.contactEmail?.trim()?.takeIf { it.isNotBlank() }
            contactPhone = request.contactPhone?.trim()?.takeIf { it.isNotBlank() }
            contactAddress = request.contactAddress?.trim()?.takeIf { it.isNotBlank() }
            this.organizer = organizer
        }
        groupRepository.persist(entity)
        return entity.toSummary()
    }

    private fun GroupEntity.toSummary(): GroupSummary =
        GroupSummary(
            id = requireNotNull(id),
            name = name,
            visibility = visibility,
            description = description,
            location = location,
            website = website,
        )
}
