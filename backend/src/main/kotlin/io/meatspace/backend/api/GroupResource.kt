package io.meatspace.backend.api

import io.meatspace.backend.service.GroupService
import io.meatspace.shared.model.CreateGroupRequest
import io.meatspace.shared.model.GroupSummary
import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.SecurityContext

@Path("/api/groups")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class GroupResource(
    private val groupService: GroupService,
) {
    @GET
    fun listPublic(): List<GroupSummary> = groupService.listPublic()

    @POST
    @RolesAllowed("organizer")
    fun create(request: CreateGroupRequest, @Context securityContext: SecurityContext): GroupSummary =
        groupService.create(request, securityContext.userPrincipal.name)
}
