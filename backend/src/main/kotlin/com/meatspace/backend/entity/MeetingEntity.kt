package com.meatspace.backend.entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.*
import java.time.Instant
import java.time.LocalDate

@Entity
@Table(name = "meetings")
class MeetingEntity : PanacheEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    var group: GroupEntity? = null

    @Column(nullable = false)
    var title: String = ""

    @Column(columnDefinition = "TEXT")
    var description: String? = null

    @Column(name = "start_time", nullable = false)
    var startTime: Instant = Instant.now()

    @Column(name = "end_time", nullable = false)
    var endTime: Instant = Instant.now()

    @Column(name = "is_public", nullable = false)
    var isPublic: Boolean = true

    @Column(name = "is_online", nullable = false)
    var isOnline: Boolean = false

    @Column
    var location: String? = null

    @Column(name = "online_link")
    var onlineLink: String? = null

    @Column(name = "recurrence_pattern")
    var recurrencePattern: String? = null

    @Column(name = "recurrence_end_date")
    var recurrenceEndDate: LocalDate? = null

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant = Instant.now()

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now()

    @ManyToMany
    @JoinTable(
        name = "meeting_attendees",
        joinColumns = [JoinColumn(name = "meeting_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var attendees: MutableSet<UserEntity> = mutableSetOf()

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }
}
