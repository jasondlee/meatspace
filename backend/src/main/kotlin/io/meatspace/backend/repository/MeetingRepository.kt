package io.meatspace.backend.repository

import io.meatspace.backend.domain.MeetingEntity
import io.meatspace.shared.model.Visibility
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class MeetingRepository : PanacheRepository<MeetingEntity> {
    fun listPublicMeetings(): List<MeetingEntity> = list("visibility", Visibility.PUBLIC)
}
