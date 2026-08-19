# Shoply development rules

- Keep the backend Onion Architecture dependency direction: infrastructure → application → domain.
- Domain code is framework-free: no Spring, JPA, HTTP, or database annotations/dependencies.
- Keep JPA entities, REST requests/responses, application DTOs, and domain models separate.
- Add database schema changes as ordered Flyway migrations; never alter a migration already shared.
- Use feature-oriented frontend code. Presentation components must not own API/business logic.
- Use the tokens in `frontend/src/styles/tokens.css`; do not introduce Tailwind unless explicitly requested.
- Validate inputs at the web boundary and return structured errors through the global exception handler.
- Never commit secrets. Extend `.env.example` whenever a new environment value is introduced.
