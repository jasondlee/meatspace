package io.meatspace.backend.security

import io.meatspace.backend.domain.UserAccountEntity
import io.smallrye.jwt.build.Jwt
import jakarta.enterprise.context.ApplicationScoped
import java.time.Duration
import java.util.Collections

@ApplicationScoped
class TokenService {
    fun issueToken(user: UserAccountEntity): String =
        Jwt.issuer("https://meatspace.local")
            .upn(user.username)
            .subject(user.id.toString())
            .groups(Collections.singleton(user.role.name.lowercase()))
            .expiresIn(Duration.ofHours(8))
            .sign()
}
