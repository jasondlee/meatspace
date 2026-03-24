package io.meatspace.backend.api

import io.meatspace.shared.model.CreateGroupRequest
import io.meatspace.shared.model.CreateMeetingRequest
import io.meatspace.shared.model.LoginRequest
import io.meatspace.shared.model.MeetingMode
import io.meatspace.shared.model.RegistrationRequest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.quarkus.test.junit.QuarkusTest
import jakarta.ws.rs.core.HttpHeaders
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import java.time.Instant

@QuarkusTest
class PlatformResourceTest {
    @Test
    fun `organizer can register create a group and publish a meeting`() {
        val token = given()
            .contentType(ContentType.JSON)
            .body(RegistrationRequest(username = "organizer1", password = "supersecret", organizer = true))
            .`when`().post("/api/auth/register")
            .then()
            .statusCode(200)
            .extract()
            .path<String>("accessToken")

        val groupId = given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            .body(CreateGroupRequest(name = "Chicago Kotlin", location = "Chicago"))
            .`when`().post("/api/groups")
            .then()
            .statusCode(200)
            .body("name", equalTo("Chicago Kotlin"))
            .extract()
            .path<Int>("id")
            .toLong()

        given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            .body(
                CreateMeetingRequest(
                    groupId = groupId,
                    title = "April Meetup",
                    startsAt = Instant.parse("2026-04-11T18:30:00Z"),
                    mode = MeetingMode.HYBRID,
                    location = "West Loop",
                    meetingUrl = "https://example.com/live",
                ),
            )
            .`when`().post("/api/meetings")
            .then()
            .statusCode(200)
            .body("title", equalTo("April Meetup"))

        given()
            .`when`().get("/api/groups")
            .then()
            .statusCode(200)
            .body("$", hasSize<Any>(1))

        given()
            .`when`().get("/api/meetings")
            .then()
            .statusCode(200)
            .body("$", hasSize<Any>(1))
    }

    @Test
    fun `member can log in but cannot create a group`() {
        given()
            .contentType(ContentType.JSON)
            .body(RegistrationRequest(username = "member1", password = "password123", organizer = false))
            .`when`().post("/api/auth/register")
            .then()
            .statusCode(200)

        val token = given()
            .contentType(ContentType.JSON)
            .body(LoginRequest(username = "member1", password = "password123"))
            .`when`().post("/api/auth/login")
            .then()
            .statusCode(200)
            .extract()
            .path<String>("accessToken")

        given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            .body(CreateGroupRequest(name = "Unauthorized Group"))
            .`when`().post("/api/groups")
            .then()
            .statusCode(403)
    }
}
