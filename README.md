University Attendance Management System (API de Control de Asistencia)
This project is a high-performance REST API developed with Java 25 and Spring Boot 4 
for managing university attendance records. 
It provides specialized tracking for Students, Administrative Staff, and Professors.
The system utilizes Hexagonal Architecture (Ports and Adapters)
to ensure business logic remains decoupled from infrastructure and technical frameworks. 
ArchitectureThe design separates technical concerns from core business rules through 
three distinct layers:Domain: The core containing pure business logic and the Attendance entity. 
No external dependencies.Application: Orchestrates Use Cases (RegisterAttendance, CompleteAttendance)
through Input and Output ports.Infrastructure: Technical implementations including:Input Adapters: 
REST Controllers and DTOs with Jakarta Validation.Output Adapters: PostgreSQL persistence (Spring Data JPA) 
and external notification Gateways.Technical SpecificationsJava 25 (LTS)Spring Boot 4.0.3Spring Data JPA / Hibernate 7
PostgreSQL 15 (Containerized)Docker & Docker ComposeSpringDoc OpenAPI / Swagger UILombokMapStruct 
Getting StartedPrerequisitesJDK 25Maven 3.9+Docker Desktop1. Database InfrastructureDeploy the PostgreSQL
container using Docker Compose:Bashdocker-compose up -d The database will be available at localhost:5433
with the name attendance_db.2. Running the ApplicationStart the service using 
the Maven wrapper:Bash./mvnw spring-boot:run The service starts on port 8080 by default. 
API DocumentationOnce the service is running, explore and test the endpoints via Swagger UI: 
http://localhost:8080/swagger-ui/index.htmlAvailable OperationsMethodEndpointDescriptionPOST/api/v1/attendanceCheck-in:
Register entry for a Student, Professor, or Admin.PATCH/api/v1/attendance/{id}/checkoutCheck-out: 
Register exit time and update status to COMPLETED.GET/api/v1/attendanceHistory: 
Retrieve all attendance records from the system. 
User ProfilesThe system is designed to handle different logic based on user roles:Estudiante (Student): 
Attendance tied to classroom locations.Catedrático (Professor): Flexible session management.
Administrativo (Admin): Workplace attendance tracking.
