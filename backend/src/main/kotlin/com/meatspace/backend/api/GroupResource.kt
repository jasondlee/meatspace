package com.meatspace.backend.api

import com.meatspace.backend.service.GroupService
import com.meatspace.dto.GroupDto
import jakarta.annotation.security.PermitAll
import jakarta.annotation.security.RolesAllowed
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.jwt.JsonWebToken
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/groups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Groups", description = "Group management endpoints")
class GroupResource(
    private val groupService: GroupService,
    private val jwt: JsonWebToken
) {

    @GET
    @PermitAll
    @Operation(summary = "List all public groups", description = "Retrieve all public groups")
    @APIResponse(
        responseCode = "200",
        description = "List of public groups",
        content = [Content(schema = Schema(implementation = GroupDto::class))]
    )
    fun listPublicGroups(): Response {
        val groups = groupService.findPublicGroups()
        return Response.ok(groups).build()
    }

    @GET
    @Path("/{id}")
    @PermitAll
    @Operation(summary = "Get group by ID", description = "Retrieve group details by ID")
    @APIResponse(
        responseCode = "200",
        description = "Group found",
        content = [Content(schema = Schema(implementation = GroupDto::class))]
    )
    @APIResponse(responseCode = "404", description = "Group not found")
    fun getGroup(@PathParam("id") id: Long): Response {
        val group = groupService.findById(id)
        return Response.ok(group).build()
    }

    @POST
    @RolesAllowed("User")
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Create a new group", description = "Create a new group with the current user as organizer")
    @APIResponse(
        responseCode = "201",
        description = "Group created",
        content = [Content(schema = Schema(implementation = GroupDto::class))]
    )
    @APIResponse(responseCode = "400", description = "Invalid input")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    fun createGroup(@Valid dto: GroupDto): Response {
        val userId = jwt.subject.toLong()
        val group = groupService.createGroup(dto, userId)
        return Response.status(Response.Status.CREATED).entity(group).build()
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("User")
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Update group", description = "Update group details (organizers only)")
    @APIResponse(
        responseCode = "200",
        description = "Group updated",
        content = [Content(schema = Schema(implementation = GroupDto::class))]
    )
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @APIResponse(responseCode = "403", description = "Forbidden - not an organizer")
    @APIResponse(responseCode = "404", description = "Group not found")
    fun updateGroup(@PathParam("id") id: Long, @Valid dto: GroupDto): Response {
        val userId = jwt.subject.toLong()
        val group = groupService.updateGroup(id, dto, userId)
        return Response.ok(group).build()
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("User")
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Delete group", description = "Delete a group (organizers only)")
    @APIResponse(responseCode = "204", description = "Group deleted")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @APIResponse(responseCode = "403", description = "Forbidden - not an organizer")
    @APIResponse(responseCode = "404", description = "Group not found")
    fun deleteGroup(@PathParam("id") id: Long): Response {
        val userId = jwt.subject.toLong()
        groupService.deleteGroup(id, userId)
        return Response.noContent().build()
    }

    @GET
    @Path("/my-groups")
    @RolesAllowed("User")
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Get my groups", description = "Get groups where the current user is an organizer")
    @APIResponse(
        responseCode = "200",
        description = "List of groups",
        content = [Content(schema = Schema(implementation = GroupDto::class))]
    )
    fun getMyGroups(): Response {
        val userId = jwt.subject.toLong()
        val groups = groupService.findByOrganizerId(userId)
        return Response.ok(groups).build()
    }
}
