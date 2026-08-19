# Catalog API

All responses are JSON.

- `GET /api/products` — catalog products
- `GET /api/products/{id}` — a catalog product
- `GET /api/products/featured` — featured products for the homepage
- `GET /api/products/search?q=keyboard` — product name search
- `GET /api/categories` — categories
- `GET /api/categories/{id}` — a category

The web catalog uses `GET /api/products` once per page load, then applies name and category filters locally. The dedicated search endpoint remains available for future server-side pagination/search.

## Authentication and cart

- `POST /api/auth/register` — accepts `{ "email", "password" }`, returns a JWT and customer identity
- `POST /api/auth/login` — accepts `{ "email", "password" }`, returns a JWT and customer identity
- `GET /api/cart` — current customer's cart
- `POST /api/cart/items` — accepts `{ "productId", "quantity" }`
- `PUT /api/cart/items/{productId}` — accepts `{ "quantity" }`
- `DELETE /api/cart/items/{productId}` — removes a current customer's cart item
- `POST /api/orders` — converts the current customer's server cart into an order (no payment capture)
- `GET /api/orders` — current customer's order history
- `GET /api/orders/{id}` — one current customer's order

Cart endpoints require `Authorization: Bearer <token>`. Payment processing is intentionally not implemented.

Unknown resources return a structured `404` response: `{ "timestamp", "status", "error", "message", "path" }`.
