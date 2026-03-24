package io.meatspace.backend.domain

import io.meatspace.shared.model.MeetingMode
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
import java.time.Instant

@Entity
@Table(name = "meeting")
open class MeetingEntity : PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    open lateinit var group: GroupEntity

    @Column(nullable = false)
    open lateinit var title: String

    @Column(columnDefinition = "text")
    open var description: String? = null

    @Column(name = "starts_at", nullable = false)
    open lateinit var startsAt: Instant

    @Column(name = "ends_at")
    open var endsAt: Instant? = null

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    open var mode: MeetingMode = MeetingMode.IN_PERSON

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    open var visibility: Visibility = Visibility.PUBLIC

    @Column
    open var location: String? = null

    @Column(name = "meeting_url")
    open var meetingUrl: String? = null

    @Column(name = "recurrence_rule")
    open var recurrenceRule: String? = null
}
