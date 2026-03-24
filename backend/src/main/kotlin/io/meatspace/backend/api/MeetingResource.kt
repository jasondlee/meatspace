package io.meatspace.backend.api
 
import io.meatspace.backend.service.MeetingService
import io.meatspace.shared.model.CreateMeetingRequest
import io.meatspace.shared.model.MeetingSummary
import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
 
@Path("/api/meetings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class MeetingResource(
    private val meetingService: MeetingService,
) {
    @GET
    fun listPublic(): List<MeetingSummary> = meetingService.listPublic()
 
    @POST
    @RolesAllowed("organizer")
    fun create(request: CreateMeetingRequest): MeetingSummary = meetingService.create(request)
}
