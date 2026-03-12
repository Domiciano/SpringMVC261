# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
./mvnw clean package

# Run application
./mvnw spring-boot:run

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=StudentServiceTest

# Run a single test method
./mvnw test -Dtest=StudentServiceTest#findStudentByCode_WhenExists_ReturnsStudent
```

The H2 console is available at `http://localhost:8080/h2` when the app is running.

## Architecture

Educational Spring Boot project demonstrating a student-course enrollment system. Layers:

- **entity/** — JPA domain models: `Student`, `Course`, `Professor`, `Enrollment`, `User`, `Role`, `Permission`, `UserRole`. Composite keys live in `entity/keys/`.
- **repository/** — Spring Data JPA repositories. `StudentRepository` and others define custom query-derived methods (e.g., `findByNameContainingIgnoreCase`, deep nested navigation like `findByRolePermissions_Role_UserRoles_User_Username`).
- **service/** — Interfaces (`StudentService`, `CourseService`, etc.) plus stub implementations (`StudentServiceImpl`, `CourseServiceImpl`) that are intentionally left inactive.
- **service/impl/** — Full working implementations (`StudentServiceImpl`, `CourseServiceImpl`, etc.). This is the active service layer wired via `@Service`.
- **controller/** — REST controllers. `ExercisesController` is the main demo of query methods; `EnrollmentController` demonstrates `@Transactional` usage.

### Dual service package pattern
There is a deliberate split: `service/` contains stub `*Impl` classes (deactivated — no `@Service`) and `service/impl/` contains the real implementations. When adding or modifying business logic, work in `service/impl/`.

## Testing

- **Unit tests** (`StudentServiceTest`, `CourseServiceTest`) use Mockito to mock repositories.
- **Integration tests** (`StudentServiceIntegrationTest`) hit a real H2 database with `create-drop` DDL.
- Test config in `src/test/resources/application.properties` disables `data.sql` initialization — tests set up their own data.
- Main app data seeded via `src/main/resources/data.sql`.
