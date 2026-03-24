package io.meatspace.backend.service

import io.meatspace.backend.domain.UserAccountEntity
import io.meatspace.backend.repository.UserAccountRepository
import io.meatspace.backend.security.PasswordHasher
import io.meatspace.backend.security.TokenService
import io.meatspace.shared.model.LoginRequest
import io.meatspace.shared.model.RegistrationRequest
import io.meatspace.shared.model.TokenResponse
import io.meatspace.shared.model.UserRole
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.NotAuthorizedException

@ApplicationScoped
class AuthService(
    private val userAccountRepository: UserAccountRepository,
    private val passwordHasher: PasswordHasher,
    private val tokenService: TokenService,
) {
    @Transactional
    fun register(request: RegistrationRequest): TokenResponse {
        if (request.username.isBlank() || request.password.length < 8) {
            throw BadRequestException("Username is required and password must be at least 8 characters.")
        }
        if (userAccountRepository.findByUsername(request.username) != null) {
            throw BadRequestException("Username is already in use.")
        }

        val user = UserAccountEntity().apply {
            username = request.username.trim()
            passwordHash = passwordHasher.hash(request.password)
            role = if (request.organizer) UserRole.ORGANIZER else UserRole.MEMBER
        }
        userAccountRepository.persist(user)

        return TokenResponse(tokenService.issueToken(user))
    }

    fun login(request: LoginRequest): TokenResponse {
        val user = userAccountRepository.findByUsername(request.username.trim())
            ?: throw NotAuthorizedException("Invalid credentials.")
        if (!passwordHasher.matches(request.password, user.passwordHash)) {
            throw NotAuthorizedException("Invalid credentials.")
        }
        return TokenResponse(tokenService.issueToken(user))
    }
}
