# Repository Guidelines

## Project Structure & Module Organization
`src/main/java/top/sakablog/nichi` contains the application code. Key packages are `controller`, `service` and `service/impl`, `repository`, `mapper`, `model`, `config`, and `common`. Runtime configuration lives in `src/main/resources/application.properties`, and static assets such as CSV seeds and avatars live under `src/main/resources/static`.

Tests mirror the main package layout under `src/test/java/top/sakablog/nichi`. Keep unit and integration tests close to the feature they validate, for example `service/BookServiceTest.java` and `controller/BookControllerIntegrationTest.java`. Build output goes to `target/` and should not be edited manually.

## Build, Test, and Development Commands
Use the Maven wrapper so contributors do not depend on a globally installed Maven version.

- `./mvnw spring-boot:run` or `mvnw.cmd spring-boot:run`: start the API locally on port `8080`.
- `./mvnw test`: run the JUnit 5 test suite.
- `./mvnw clean package`: compile, run tests, and build the executable jar in `target/`.
- `./mvnw -Dtest=BookServiceTest test`: run one focused test class while iterating.

## Coding Style & Naming Conventions
Follow the existing Java style: 4-space indentation, one top-level public class per file, and package names all lowercase. Use `PascalCase` for classes, `camelCase` for methods and fields, and append DTO/test roles explicitly, such as `BookCreateDto` or `WordMapperTest`.

Controllers should stay thin and delegate business logic to services. Keep REST paths versioned under `/api/v1/...`, matching the current controllers.

## Testing Guidelines
This project uses `spring-boot-starter-test` with JUnit 5. Name pure unit tests `*Test` and Spring context or database-backed tests `*IntegrationTest`. Add tests beside the affected package, and cover controller, service, and repository changes when behavior crosses layers.

Prefer deterministic test data from `src/test/resources`. If a change depends on MySQL or Redis settings, document the requirement and add an isolated test alternative where possible.

## Commit & Pull Request Guidelines
Recent history follows Conventional Commit style with scopes, for example `feat(model): ...` and `feat(controller/service): ...`. Keep that format for new commits: `type(scope): short summary`.

Pull requests should include a concise description, affected endpoints or modules, linked issues if any, and sample requests or screenshots when API behavior changes. Call out config changes clearly, especially anything touching `application.properties`, database schema, or authentication.

## Security & Configuration Tips
`application.properties` currently contains local database and Redis settings. Do not commit real credentials or environment-specific secrets. Prefer environment overrides for sensitive values, and treat SQL files such as `ddl_User.sql` as reviewed schema artifacts, not ad hoc scratch files.
