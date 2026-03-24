package com.meatspace.backend.entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "groups")
class GroupEntity : PanacheEntity() {
    @Column(nullable = false)
    var name: String = ""

    @Column(columnDefinition = "TEXT")
    var description: String? = null

    @Column(name = "is_public", nullable = false)
    var isPublic: Boolean = true

    @Column(name = "logo_url")
    var logoUrl: String? = null

    @Column
    var website: String? = null

    @Column
    var location: String? = null

    @Column(name = "contact_email")
    var contactEmail: String? = null

    @Column(name = "contact_phone")
    var contactPhone: String? = null

    @Column(name = "contact_address", columnDefinition = "TEXT")
    var contactAddress: String? = null

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant = Instant.now()

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now()

    @ManyToMany
    @JoinTable(
        name = "group_organizers",
        joinColumns = [JoinColumn(name = "group_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var organizers: MutableSet<UserEntity> = mutableSetOf()

    @OneToMany(mappedBy = "group", cascade = [CascadeType.ALL], orphanRemoval = true)
    var meetings: MutableList<MeetingEntity> = mutableListOf()

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }
}
