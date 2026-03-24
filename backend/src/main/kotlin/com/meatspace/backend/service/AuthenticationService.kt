package com.meatspace.backend.service

import com.meatspace.backend.entity.UserEntity
import com.meatspace.backend.repository.UserRepository
import com.meatspace.backend.security.JwtService
import com.meatspace.common.Constants
import com.meatspace.dto.LoginRequest
import com.meatspace.dto.RegisterRequest
import com.meatspace.dto.TokenResponse
import com.meatspace.dto.UserDto
import io.quarkus.elytron.security.common.BcryptUtil
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.core.Response
import java.time.Instant

@ApplicationScoped
class AuthenticationService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {
    @Transactional
    fun register(request: RegisterRequest): TokenResponse {
        if (userRepository.existsByUsername(request.username)) {
            throw WebApplicationException(
                "Username already exists",
                Response.Status.CONFLICT
            )
        }

        if (userRepository.existsByEmail(request.email)) {
            throw WebApplicationException(
                "Email already exists",
                Response.Status.CONFLICT
            )
        }

        val user = UserEntity().apply {
            username = request.username
            email = request.email
            passwordHash = BcryptUtil.bcryptHash(request.password)
            createdAt = Instant.now()
            updatedAt = Instant.now()
        }

        userRepository.persist(user)

        val token = jwtService.generateToken(user)
        return TokenResponse(
            token = token,
            expiresIn = jwtService.getExpirationTime(),
            user = user.toDto()
        )
    }

    fun login(request: LoginRequest): TokenResponse {
        val user = userRepository.findByUsername(request.username)
            ?: throw WebApplicationException(
                "Invalid credentials",
                Response.Status.UNAUTHORIZED
            )

        if (!BcryptUtil.matches(request.password, user.passwordHash)) {
            throw WebApplicationException(
                "Invalid credentials",
                Response.Status.UNAUTHORIZED
            )
        }

        val token = jwtService.generateToken(user)
        return TokenResponse(
            token = token,
            expiresIn = jwtService.getExpirationTime(),
            user = user.toDto()
        )
    }

    private fun UserEntity.toDto() = UserDto(
        id = id,
        username = username,
        email = email,
        createdAt = createdAt.toString()
    )
}
