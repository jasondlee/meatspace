package com.meatspace.backend.entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "users")
class UserEntity : PanacheEntity() {
    @Column(nullable = false, unique = true)
    var username: String = ""

    @Column(nullable = false, unique = true)
    var email: String = ""

    @Column(name = "password_hash", nullable = false)
    var passwordHash: String = ""

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant = Instant.now()

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now()

    @ManyToMany(mappedBy = "organizers")
    var organizerOf: MutableSet<GroupEntity> = mutableSetOf()

    @ManyToMany(mappedBy = "attendees")
    var attendingMeetings: MutableSet<MeetingEntity> = mutableSetOf()

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }
}
