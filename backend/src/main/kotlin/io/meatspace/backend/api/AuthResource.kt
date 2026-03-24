package io.meatspace.backend.api

import io.meatspace.backend.service.AuthService
import io.meatspace.shared.model.LoginRequest
import io.meatspace.shared.model.RegistrationRequest
import io.meatspace.shared.model.TokenResponse
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/api/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class AuthResource(
    private val authService: AuthService,
) {
    @POST
    @Path("/register")
    fun register(request: RegistrationRequest): TokenResponse = authService.register(request)

    @POST
    @Path("/login")
    fun login(request: LoginRequest): TokenResponse = authService.login(request)
}
