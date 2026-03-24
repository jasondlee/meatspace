package com.meatspace.backend.api

import com.meatspace.backend.service.CalendarService
import jakarta.annotation.security.PermitAll
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/calendar")
@Tag(name = "Calendar", description = "Calendar export endpoints")
class CalendarResource(private val calendarService: CalendarService) {

    @GET
    @Path("/groups/{groupId}")
    @Produces("text/calendar")
    @PermitAll
    @Operation(summary = "Get group calendar", description = "Export group meetings as iCalendar (.ics) format")
    @APIResponse(responseCode = "200", description = "iCalendar file")
    @APIResponse(responseCode = "404", description = "Group not found")
    fun getGroupCalendar(@PathParam("groupId") groupId: Long): Response {
        val ical = calendarService.generateICalForGroup(groupId)
        return Response.ok(ical)
            .header("Content-Disposition", "attachment; filename=\"group-$groupId-calendar.ics\"")
            .build()
    }
}
