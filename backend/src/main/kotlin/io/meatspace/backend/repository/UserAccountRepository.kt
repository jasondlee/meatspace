package io.meatspace.backend.repository

import io.meatspace.backend.domain.UserAccountEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserAccountRepository : PanacheRepository<UserAccountEntity> {
    fun findByUsername(username: String): UserAccountEntity? = find("username", username).firstResult()
}
