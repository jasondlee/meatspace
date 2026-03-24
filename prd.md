# Overview
Meatspace is an online community planning platform. The primary goal of the platform is to allow communities to schedule meetings and events and publicize them to their users.

# Core Features
- Meetings/Events
  - Meetings must have a time and date
  - In-person meetings must have a location
  - Online meetings must have a link
  - Meetings can be public or private
  - Meetings can be scheduled for a specific date or for a recurring pattern
- Groups
  - Groups must have a name
  - Groups can be public or private
  - Groups must have at least one organizer
  - Groups can have a description
  - Groups can have a logo
  - Groups can have a website
  - Groups can have a location
  - Groups can have a contact email
  - Groups can have a contact phone number
  - Groups can have a contact address
- User accounts
  - Users must have a username and password
  - Users can view and join meetings that are public
  - Users can be group organizers but do not need to be
  - Users can sign up for an account
- Calendar
  - Each group will have its own calendar
  - An ics will be generated for each group

# User Experience
Key User Flows:
- Group creation
  - User signs up for an organizer account
  - User creates a group, being prompted for all relevant information
- Meeting creation
  - Organizer creates a meeting
  - Organizer is given the opportunity to preview the meeting before publishing
  - Organizer can publish the meeting
  - Organizer can edit the meeting
  - Organizer can delete the meeting
- User sign up
  - Users can create accounts
  - Users can search for meetings and groups that are of intereest
  - Users can join meetings and groups

UI/UX Considerations:
- Clean, minimalist interface
- Intuitive context visualization
- Keyboard shortcuts for power users
- Responsive design for various screen sizes

# Technical Architecture
System Components:
1. Backend Server
  - Quarkus-based
    - Rest endpoints will be secured using JWT
  - Exposes RESTful API, with OpenAPI documentation
  - Stores data in a relational database, using Hibernate ORM
2. Storage
  - PostgreSQL will be the default database
  - Database migrations will be managed by Flyway
3. User interfaces
  - The user interface will be built using Compose Multiplatform, targeting Android, iOS, and Web
  - The user interface will be responsive and mobile-friendly
  - The web UI will use Tailwind CSS for styling

Technical Specifications:
- Build will be based on Maven
- Single root Maven project with submodules: shared, backend, uis
- All code will be written in Kotlin
- All dependencies will be managed using Maven
- All tests will be written in JUnit 5
- All dependencies, including build tools and plugins, will use the latest stable versions
