package com.meatspace.backend.security

import com.meatspace.backend.entity.UserEntity
import com.meatspace.common.Constants
import io.smallrye.jwt.build.Jwt
import jakarta.enterprise.context.ApplicationScoped
import java.time.Instant
import java.time.temporal.ChronoUnit

@ApplicationScoped
class JwtService {
    fun generateToken(user: UserEntity): String {
        val now = Instant.now()
        val expiresAt = now.plus(Constants.JWT.EXPIRATION_TIME, ChronoUnit.SECONDS)

        return Jwt.issuer(Constants.JWT.ISSUER)
            .subject(user.id.toString())
            .claim("username", user.username)
            .claim("email", user.email)
            .issuedAt(now)
            .expiresAt(expiresAt)
            .sign()
    }

    fun getExpirationTime(): Long {
        return Constants.JWT.EXPIRATION_TIME
    }
}
