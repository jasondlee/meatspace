package com.meatspace.backend

import io.quarkus.runtime.Quarkus
import io.quarkus.runtime.QuarkusApplication
import io.quarkus.runtime.annotations.QuarkusMain
import jakarta.ws.rs.core.Application
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition
import org.eclipse.microprofile.openapi.annotations.info.Info
import org.eclipse.microprofile.openapi.annotations.servers.Server

@QuarkusMain
class MeatspaceApplication : QuarkusApplication {
    override fun run(vararg args: String?): Int {
        Quarkus.waitForExit()
        return 0
    }
}

@OpenAPIDefinition(
    info = Info(
        title = "Meatspace API",
        version = "1.0.0",
        description = "Community planning and event management platform"
    ),
    servers = [
        Server(url = "http://localhost:8080", description = "Development server"),
        Server(url = "https://api.meatspace.com", description = "Production server")
    ]
)
class ApiApplication : Application()

fun main(args: Array<String>) {
    Quarkus.run(MeatspaceApplication::class.java, *args)
}
