package com.meatspace.backend.service

import com.meatspace.backend.entity.UserEntity
import com.meatspace.backend.repository.UserRepository
import com.meatspace.dto.UserDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.time.Instant

@ApplicationScoped
class UserService(private val userRepository: UserRepository) {

    fun findById(id: Long): UserDto {
        val user = userRepository.findById(id)
            ?: throw NotFoundException("User not found")
        return user.toDto()
    }

    fun findByUsername(username: String): UserDto? {
        return userRepository.findByUsername(username)?.toDto()
    }

    @Transactional
    fun updateUser(id: Long, dto: UserDto): UserDto {
        val user = userRepository.findById(id)
            ?: throw NotFoundException("User not found")

        user.username = dto.username
        user.email = dto.email
        user.updatedAt = Instant.now()

        userRepository.persist(user)
        return user.toDto()
    }

    @Transactional
    fun deleteUser(id: Long) {
        if (!userRepository.deleteById(id)) {
            throw NotFoundException("User not found")
        }
    }

    private fun UserEntity.toDto() = UserDto(
        id = id,
        username = username,
        email = email,
        createdAt = createdAt.toString()
    )
}
