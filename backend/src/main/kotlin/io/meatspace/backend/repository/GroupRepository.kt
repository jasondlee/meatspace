package io.meatspace.backend.repository

import io.meatspace.backend.domain.GroupEntity
import io.meatspace.shared.model.Visibility
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class GroupRepository : PanacheRepository<GroupEntity> {
    fun listPublicGroups(): List<GroupEntity> = list("visibility", Visibility.PUBLIC)
}
