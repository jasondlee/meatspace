package com.meatspace.backend.repository

import com.meatspace.backend.entity.UserEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository : PanacheRepository<UserEntity> {
    fun findByUsername(username: String): UserEntity? {
        return find("username", username).firstResult()
    }

    fun findByEmail(email: String): UserEntity? {
        return find("email", email).firstResult()
    }

    fun existsByUsername(username: String): Boolean {
        return count("username", username) > 0
    }

    fun existsByEmail(email: String): Boolean {
        return count("email", email) > 0
    }
}
