-- Additional catalog examples matching the Shoply Figma product collection.
INSERT INTO categories (id, name, slug, icon, item_count) VALUES
  ('00000000-0000-0000-0000-000000000006', 'Office', 'office', 'Laptop', 1280),
  ('00000000-0000-0000-0000-000000000007', 'Accessories', 'accessories', 'BookOpen', 890)
ON CONFLICT (slug) DO NOTHING;

INSERT INTO products (id, category_id, name, slug, description, price, previous_price, rating, image_url, featured) VALUES
  ('10000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000001', 'Ultra-Wide Curve Monitor 34”', 'ultra-wide-curve-monitor-34', 'Immersive curved display for focused work, creative editing, and modern desks.', 699.00, 799.00, 4.9, 'https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?auto=format&fit=crop&w=900&q=80', true),
  ('10000000-0000-0000-0000-000000000006', '00000000-0000-0000-0000-000000000003', 'Leather Tech Desk Mat', 'leather-tech-desk-mat', 'A soft, durable desk mat that brings a composed finish to your workspace.', 69.00, NULL, 4.4, 'https://images.unsplash.com/photo-1593642532744-d377ab507dc8?auto=format&fit=crop&w=900&q=80', false),
  ('10000000-0000-0000-0000-000000000007', '00000000-0000-0000-0000-000000000003', 'Modern Lounge Chair', 'modern-lounge-chair', 'Comfort-forward lounge seating with a clean Scandinavian silhouette.', 289.00, 329.00, 4.7, 'https://images.unsplash.com/photo-1555041469-a586c61ea9bc?auto=format&fit=crop&w=900&q=80', false),
  ('10000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000007', 'USB-C Braided Cable', 'usb-c-braided-cable', 'Fast-charging, tangle-resistant cable finished for daily carry.', 24.00, NULL, 4.6, 'https://images.unsplash.com/photo-1583863788434-e58a36330cf0?auto=format&fit=crop&w=900&q=80', false),
  ('10000000-0000-0000-0000-000000000009', '00000000-0000-0000-0000-000000000001', 'Wireless Studio Earbuds', 'wireless-studio-earbuds', 'Compact wireless earbuds with balanced sound and all-day comfort.', 149.00, 179.00, 4.7, 'https://images.unsplash.com/photo-1606220588913-b3aacb4d2f46?auto=format&fit=crop&w=900&q=80', false),
  ('10000000-0000-0000-0000-000000000010', '00000000-0000-0000-0000-000000000006', 'Aluminum Laptop Stand', 'aluminum-laptop-stand', 'Elevated aluminum support for a more ergonomic workstation.', 79.00, NULL, 4.5, 'https://images.unsplash.com/photo-1527814050087-3793815479db?auto=format&fit=crop&w=900&q=80', false)
ON CONFLICT (slug) DO NOTHING;

INSERT INTO inventory (product_id, quantity) VALUES
  ('10000000-0000-0000-0000-000000000005', 18),
  ('10000000-0000-0000-0000-000000000006', 36),
  ('10000000-0000-0000-0000-000000000007', 9),
  ('10000000-0000-0000-0000-000000000008', 64),
  ('10000000-0000-0000-0000-000000000009', 22),
  ('10000000-0000-0000-0000-000000000010', 27)
ON CONFLICT (product_id) DO NOTHING;
