# Development

Requirements: Java 21, Maven 3.9+, Node 20+, npm, and a local PostgreSQL 16+ installation.

```powershell
cd backend; mvn test
cd ../frontend; npm install; npm run build
```

Create a `shoply` database and configure its connection values from `.env.example`. Run the API with `mvn spring-boot:run` and the web client with `npm run dev`. Flyway runs automatically when Spring Boot connects to PostgreSQL.
