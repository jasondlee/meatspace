package com.meatspace.backend.repository

import com.meatspace.backend.entity.GroupEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class GroupRepository : PanacheRepository<GroupEntity> {
    fun findPublicGroups(): List<GroupEntity> {
        return list("isPublic", true)
    }

    fun findByName(name: String): List<GroupEntity> {
        return list("lower(name) like lower(?1)", "%$name%")
    }

    fun findByOrganizerId(userId: Long): List<GroupEntity> {
        return find("select g from GroupEntity g join g.organizers o where o.id = ?1", userId).list()
    }

    fun isUserOrganizer(groupId: Long, userId: Long): Boolean {
        return find(
            "select count(g) from GroupEntity g join g.organizers o where g.id = ?1 and o.id = ?2",
            groupId, userId
        ).count() > 0
    }
}
