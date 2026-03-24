package com.meatspace.backend.api

import com.meatspace.backend.service.AuthenticationService
import com.meatspace.dto.LoginRequest
import com.meatspace.dto.RegisterRequest
import com.meatspace.dto.TokenResponse
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Authentication", description = "Authentication and registration endpoints")
class AuthResource(private val authenticationService: AuthenticationService) {

    @POST
    @Path("/register")
    @Operation(summary = "Register a new user", description = "Create a new user account")
    @APIResponse(
        responseCode = "200",
        description = "User successfully registered",
        content = [Content(schema = Schema(implementation = TokenResponse::class))]
    )
    @APIResponse(responseCode = "409", description = "Username or email already exists")
    @APIResponse(responseCode = "400", description = "Invalid input")
    fun register(@Valid request: RegisterRequest): Response {
        val tokenResponse = authenticationService.register(request)
        return Response.ok(tokenResponse).build()
    }

    @POST
    @Path("/login")
    @Operation(summary = "Login", description = "Authenticate and receive JWT token")
    @APIResponse(
        responseCode = "200",
        description = "Successfully authenticated",
        content = [Content(schema = Schema(implementation = TokenResponse::class))]
    )
    @APIResponse(responseCode = "401", description = "Invalid credentials")
    fun login(@Valid request: LoginRequest): Response {
        val tokenResponse = authenticationService.login(request)
        return Response.ok(tokenResponse).build()
    }
}
