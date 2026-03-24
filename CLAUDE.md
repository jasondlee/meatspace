# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Meatspace is an online community planning platform for scheduling meetings and events. The codebase is a multi-module project with a Quarkus backend and Compose Multiplatform frontend.

## Build System Architecture

This project uses TWO build systems:
- **Maven** for the backend and shared modules (root pom.xml)
- **Gradle** for the UI modules (uis/ directory with Gradle wrapper)

The UI build is NOT integrated into the Maven build by default. Use the `build-uis` profile if needed.

## Module Structure

```
meatspace/
├── shared/          # Domain models and DTOs (Maven module)
├── backend/         # Quarkus REST API (Maven module)
└── uis/             # Compose Multiplatform UIs (Gradle project)
    ├── shared/      # Shared UI components
    ├── androidApp/  # Android application
    └── webApp/      # Web application
```

## Common Development Commands

### Backend Development

**Build the backend:**
```bash
mvn clean install
```

**Run backend in dev mode (with hot reload):**
```bash
cd backend
mvn quarkus:dev
```
The backend runs on http://localhost:8080

**Run tests:**
```bash
mvn test                    # Unit tests only
mvn verify                  # Unit + integration tests
mvn test -Dtest=ClassName   # Single test class
```

**Access API documentation:**
- OpenAPI spec: http://localhost:8080/openapi
- Swagger UI: http://localhost:8080/swagger-ui

### UI Development

**Build all UI modules:**
```bash
cd uis
./gradlew build
```

**Run web application:**
```bash
cd uis
./gradlew :webApp:wasmJsBrowserDevelopmentRun
```

**Build Android application:**
```bash
cd uis
./gradlew :androidApp:assembleDebug
```

### Database

**PostgreSQL connection defaults:**
- Host: localhost:5432
- Database: meatspace
- Username: postgres
- Password: postgres

Override via environment variables: DB_HOST, DB_PORT, DB_NAME, DB_USERNAME, DB_PASSWORD

**Database migrations:**
- Located in: backend/src/main/resources/db/migration/
- Run automatically on backend startup (quarkus.flyway.migrate-at-start=true)
- Naming: V{number}__{description}.sql

## Key Technical Details

### Backend Architecture

**Layers:**
1. **API Resources** (backend/api/) - JAX-RS REST endpoints
2. **Services** (backend/service/) - Business logic
3. **Repositories** (backend/repository/) - Data access using Panache
4. **Entities** (backend/entity/) - Hibernate ORM entities

**Authentication:**
- JWT-based authentication using SmallRye JWT
- Private key: backend/src/main/resources/privateKey.pem
- Public key: backend/src/main/resources/META-INF/resources/publicKey.pem
- Token expiration: 24 hours (86400 seconds)
- Auth endpoints in AuthResource.kt

**CORS Configuration:**
- Enabled for http://localhost:3000 and http://localhost:8081
- Credentials allowed for authenticated requests

### Shared Module

- Contains domain models (com.meatspace.domain) and DTOs (com.meatspace.dto)
- Uses Kotlinx Serialization (NOT Jackson)
- Custom serializers for Instant and LocalDate types
- Shared between backend (via Maven) and UIs (via mavenLocal)

### UI Architecture

**Compose Multiplatform setup:**
- Kotlin Multiplatform with targets: Android, iOS (Darwin), JS/Wasm
- Shared UI code in uis/shared/src/commonMain/
- Platform-specific code in androidMain, iosMain, jsMain
- Uses Ktor client for HTTP requests with platform-specific engines

**Important:** The UI build uses Android Gradle Plugin 9.1.0+ as specified in the PRD.

## Domain Model

**Core entities:**
- **User**: Authentication and user management
- **Group**: Community groups with organizers
- **Meeting**: Events/meetings belonging to groups

**Relationships:**
- Groups have many-to-many relationship with Users (organizers) via group_organizers
- Meetings belong to Groups (many-to-one)
- Meetings have many-to-many relationship with Users (attendees) via meeting_attendees

**Business Rules:**
- Meetings must have either location (in-person) or onlineLink (online)
- Groups must have at least one organizer
- Users can be organizers without being regular users

## Testing

**Backend testing:**
- Unit tests use JUnit 5 and MockK
- Integration tests use RestAssured and Quarkus test framework
- H2 in-memory database for tests (quarkus-test-h2)
- Test files: src/test/kotlin/**/*Test.kt

**Naming conventions:**
- Unit tests: *Test.kt
- Integration tests: *IT.kt (run with maven-failsafe-plugin)

## Configuration

**Backend configuration:**
- application.properties in backend/src/main/resources/
- Environment variable substitution pattern: ${VAR_NAME:default_value}
- Dev mode enables Quarkus DevServices for automatic PostgreSQL container

**Important settings:**
- Hibernate: database.generation=none (managed by Flyway)
- Logging: DEBUG for com.meatspace package, INFO for others
- Jackson: non-null serialization, ignores unknown properties

## Code Conventions

- All code is written in Kotlin (no Java)
- Java 21 target for backend
- Kotlin compiler plugins used:
  - Backend: all-open and no-arg for JPA entities
  - Shared: kotlinx-serialization
  - UIs: Compose compiler plugin

## Known Quirks

- The uis/ directory has its own .gitignore and is built separately from Maven
- To publish shared module changes to UIs, run `mvn install` from root to update mavenLocal
- JWT keys are committed in the repo (privateKey.pem, publicKey.pem) - these are for development only
- Quarkus dev mode hot-reloads Java/Kotlin changes but may require restart for resource changes
