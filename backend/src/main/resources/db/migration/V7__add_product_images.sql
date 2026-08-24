CREATE TABLE product_images (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    image_url VARCHAR(1000) NOT NULL,
    position INTEGER NOT NULL CHECK (position >= 0 AND position < 3),
    CONSTRAINT uk_product_images_product_position UNIQUE (product_id, position)
);

CREATE INDEX idx_product_images_product_id ON product_images(product_id);
