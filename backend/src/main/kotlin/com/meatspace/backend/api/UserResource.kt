package com.meatspace.backend.api

import com.meatspace.backend.service.UserService
import com.meatspace.dto.UserDto
import jakarta.annotation.security.RolesAllowed
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.SecurityContext
import org.eclipse.microprofile.jwt.JsonWebToken
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Users", description = "User management endpoints")
class UserResource(
    private val userService: UserService,
    private val jwt: JsonWebToken
) {

    @GET
    @Path("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve user details by ID")
    @APIResponse(
        responseCode = "200",
        description = "User found",
        content = [Content(schema = Schema(implementation = UserDto::class))]
    )
    @APIResponse(responseCode = "404", description = "User not found")
    fun getUser(@PathParam("id") id: Long): Response {
        val user = userService.findById(id)
        return Response.ok(user).build()
    }

    @GET
    @Path("/me")
    @RolesAllowed("User")
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Get current user", description = "Get the currently authenticated user's details")
    @APIResponse(
        responseCode = "200",
        description = "Current user details",
        content = [Content(schema = Schema(implementation = UserDto::class))]
    )
    @APIResponse(responseCode = "401", description = "Unauthorized")
    fun getCurrentUser(): Response {
        val userId = jwt.subject.toLong()
        val user = userService.findById(userId)
        return Response.ok(user).build()
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("User")
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Update user", description = "Update user details")
    @APIResponse(
        responseCode = "200",
        description = "User updated",
        content = [Content(schema = Schema(implementation = UserDto::class))]
    )
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @APIResponse(responseCode = "403", description = "Forbidden")
    @APIResponse(responseCode = "404", description = "User not found")
    fun updateUser(
        @PathParam("id") id: Long,
        @Valid dto: UserDto,
        @Context securityContext: SecurityContext
    ): Response {
        val currentUserId = jwt.subject.toLong()
        if (currentUserId != id) {
            throw ForbiddenException("You can only update your own profile")
        }

        val updatedUser = userService.updateUser(id, dto)
        return Response.ok(updatedUser).build()
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("User")
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Delete user", description = "Delete user account")
    @APIResponse(responseCode = "204", description = "User deleted")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @APIResponse(responseCode = "403", description = "Forbidden")
    @APIResponse(responseCode = "404", description = "User not found")
    fun deleteUser(
        @PathParam("id") id: Long,
        @Context securityContext: SecurityContext
    ): Response {
        val currentUserId = jwt.subject.toLong()
        if (currentUserId != id) {
            throw ForbiddenException("You can only delete your own account")
        }

        userService.deleteUser(id)
        return Response.noContent().build()
    }
}
