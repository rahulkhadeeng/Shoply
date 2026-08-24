# Database

PostgreSQL is the supported database. Flyway migrations are in `backend/src/main/resources/db/migration` and are applied in filename order.

`V1__create_catalog_schema.sql` creates `categories`, `products`, `users`, `roles`, `user_roles`, and `inventory`—a deliberately small base for future account and order work. `V2` and `V6` seed the Figma-inspired catalog collection. `V3__add_commerce_tables.sql` adds addresses, carts, cart items, orders, and immutable order line items; it intentionally contains no payment tables. `V4` and `V5` seed the `CUSTOMER` and `ADMIN` roles; no default administrator account is created.
