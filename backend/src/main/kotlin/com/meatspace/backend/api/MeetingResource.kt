package com.meatspace.backend.api

import com.meatspace.backend.service.MeetingService
import com.meatspace.dto.MeetingDto
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

@Path("/api/meetings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Meetings", description = "Meeting management endpoints")
class MeetingResource(
    private val meetingService: MeetingService,
    private val jwt: JsonWebToken
) {

    @GET
    @PermitAll
    @Operation(summary = "List upcoming public meetings", description = "Retrieve all upcoming public meetings")
    @APIResponse(
        responseCode = "200",
        description = "List of upcoming public meetings",
        content = [Content(schema = Schema(implementation = MeetingDto::class))]
    )
    fun listUpcomingPublicMeetings(): Response {
        val meetings = meetingService.findUpcomingPublicMeetings()
        return Response.ok(meetings).build()
    }

    @GET
    @Path("/{id}")
    @PermitAll
    @Operation(summary = "Get meeting by ID", description = "Retrieve meeting details by ID")
    @APIResponse(
        responseCode = "200",
        description = "Meeting found",
        content = [Content(schema = Schema(implementation = MeetingDto::class))]
    )
    @APIResponse(responseCode = "404", description = "Meeting not found")
    fun getMeeting(@PathParam("id") id: Long): Response {
        val meeting = meetingService.findById(id)
        return Response.ok(meeting).build()
    }

    @GET
    @Path("/group/{groupId}")
    @PermitAll
    @Operation(summary = "List meetings by group", description = "Retrieve all meetings for a specific group")
    @APIResponse(
        responseCode = "200",
        description = "List of meetings",
        content = [Content(schema = Schema(implementation = MeetingDto::class))]
    )
    fun listMeetingsByGroup(@PathParam("groupId") groupId: Long): Response {
        val meetings = meetingService.findByGroupId(groupId)
        return Response.ok(meetings).build()
    }

    @POST
    @RolesAllowed("User")
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Create a new meeting", description = "Create a new meeting (group organizers only)")
    @APIResponse(
        responseCode = "201",
        description = "Meeting created",
        content = [Content(schema = Schema(implementation = MeetingDto::class))]
    )
    @APIResponse(responseCode = "400", description = "Invalid input")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @APIResponse(responseCode = "403", description = "Forbidden - not a group organizer")
    fun createMeeting(@Valid dto: MeetingDto): Response {
        val userId = jwt.subject.toLong()
        val meeting = meetingService.createMeeting(dto, userId)
        return Response.status(Response.Status.CREATED).entity(meeting).build()
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("User")
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Update meeting", description = "Update meeting details (group organizers only)")
    @APIResponse(
        responseCode = "200",
        description = "Meeting updated",
        content = [Content(schema = Schema(implementation = MeetingDto::class))]
    )
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @APIResponse(responseCode = "403", description = "Forbidden - not a group organizer")
    @APIResponse(responseCode = "404", description = "Meeting not found")
    fun updateMeeting(@PathParam("id") id: Long, @Valid dto: MeetingDto): Response {
        val userId = jwt.subject.toLong()
        val meeting = meetingService.updateMeeting(id, dto, userId)
        return Response.ok(meeting).build()
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("User")
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Delete meeting", description = "Delete a meeting (group organizers only)")
    @APIResponse(responseCode = "204", description = "Meeting deleted")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @APIResponse(responseCode = "403", description = "Forbidden - not a group organizer")
    @APIResponse(responseCode = "404", description = "Meeting not found")
    fun deleteMeeting(@PathParam("id") id: Long): Response {
        val userId = jwt.subject.toLong()
        meetingService.deleteMeeting(id, userId)
        return Response.noContent().build()
    }
}
