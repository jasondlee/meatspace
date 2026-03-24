package io.meatspace.backend.domain

import io.meatspace.shared.model.UserRole
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user_account")
open class UserAccountEntity : PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @Column(nullable = false, unique = true)
    open lateinit var username: String

    @Column(name = "password_hash", nullable = false)
    open lateinit var passwordHash: String

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    open var role: UserRole = UserRole.MEMBER
}
