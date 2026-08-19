# Architecture

The backend follows Onion Architecture. `domain` contains pure business models and repository ports. `application` implements use cases against those ports. `infrastructure` adapts ports to Spring MVC, JPA, PostgreSQL, Flyway, and configuration. Dependencies only point inward.

The initial catalog slice has pure `Product` and `Category` models, query ports, application query services, persistence entities/repositories/mappers, and REST controllers. API response records are intentionally separate from domain objects and persistence entities.

The frontend uses a feature-oriented React structure. Shared layout/UI components remain under `components`; catalog API/data and feature components remain under `features/catalog`.

The `features/cart` module owns cart state behind a React context. This provides a complete local workflow today and keeps cart consumers independent of its storage mechanism, so a future REST cart adapter can replace local storage without restructuring page components.
