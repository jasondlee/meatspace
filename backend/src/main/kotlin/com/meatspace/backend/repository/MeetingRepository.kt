package com.meatspace.backend.repository

import com.meatspace.backend.entity.MeetingEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import java.time.Instant

@ApplicationScoped
class MeetingRepository : PanacheRepository<MeetingEntity> {
    fun findByGroupId(groupId: Long): List<MeetingEntity> {
        return find("group.id", groupId).list()
    }

    fun findPublicMeetings(): List<MeetingEntity> {
        return list("isPublic", true)
    }

    fun findUpcomingMeetings(): List<MeetingEntity> {
        val now = Instant.now()
        return find("startTime > ?1 order by startTime asc", now).list()
    }

    fun findUpcomingPublicMeetings(): List<MeetingEntity> {
        val now = Instant.now()
        return find("isPublic = true and startTime > ?1 order by startTime asc", now).list()
    }

    fun findByGroupIdAndDateRange(groupId: Long, start: Instant, end: Instant): List<MeetingEntity> {
        return find(
            "group.id = ?1 and startTime >= ?2 and startTime <= ?3 order by startTime asc",
            groupId, start, end
        ).list()
    }
}
