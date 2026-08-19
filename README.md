# Shoply

Shoply is a React + Spring Boot e-commerce foundation with a working catalog homepage vertical slice.

## Available catalog experience

- Homepage with backend-fed categories and featured products
- Product browsing at `/products`, with client-side name search and category filtering
- Product details at `/products/:productId`, loaded from the catalog API
- Persistent client-side cart at `/cart` and checkout at `/checkout`

Cart and checkout are currently frontend workflows. The cart state is stored in browser local storage. The database schema is ready for persistent carts/orders; payment processing is intentionally out of scope.

The API supports customer registration/login using BCrypt password hashes and signed JWT access tokens. The frontend has `/login` and `/register` flows with a persistent local session. Server cart endpoints are authenticated and persist against PostgreSQL; guest cart migration to those endpoints remains the next integration step.

## Quick start

1. Create a local PostgreSQL database named `shoply` and a database user matching `.env.example`.
2. Copy `.env.example` values into your local environment as needed.
3. Start the API: `cd backend && mvn spring-boot:run`
4. Start the web app: `cd frontend && npm install && npm run dev`
5. Open the URL printed by Vite (normally `http://localhost:5173`).

The frontend reads `VITE_API_BASE_URL`, which defaults to `http://localhost:8080/api`.

See [architecture](docs/architecture.md), [development](docs/development.md), [API](docs/api.md), [database](docs/database.md), and [Figma notes](docs/figma.md).
