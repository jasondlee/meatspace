package io.meatspace.backend.domain

import io.meatspace.shared.model.Visibility
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "community_group")
open class GroupEntity : PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @Column(nullable = false)
    open lateinit var name: String

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    open var visibility: Visibility = Visibility.PUBLIC

    @Column(columnDefinition = "text")
    open var description: String? = null

    @Column(name = "logo_url")
    open var logoUrl: String? = null

    @Column
    open var website: String? = null

    @Column
    open var location: String? = null

    @Column(name = "contact_email")
    open var contactEmail: String? = null

    @Column(name = "contact_phone")
    open var contactPhone: String? = null

    @Column(name = "contact_address")
    open var contactAddress: String? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organizer_id", nullable = false)
    open lateinit var organizer: UserAccountEntity
}
