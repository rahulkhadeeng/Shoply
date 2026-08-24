# Development admin access

Register a normal account first. To grant that existing account admin access in a local database, run this SQL with the account email substituted (do not use this pattern in production):

```sql
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u CROSS JOIN roles r
WHERE u.email = 'admin@example.com' AND r.name = 'ADMIN'
ON CONFLICT DO NOTHING;
```

Sign out and back in to receive an updated JWT with the `ADMIN` role. Admin product creation is available at `POST /api/admin/products` and creates initial inventory together with the product.
