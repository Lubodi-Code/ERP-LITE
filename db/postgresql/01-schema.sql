-- Extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pg_trgm";

-- Products
CREATE TABLE IF NOT EXISTS products (
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    sku         VARCHAR(50)     NOT NULL UNIQUE,
    name        VARCHAR(200)    NOT NULL,
    description TEXT,
    price       DECIMAL(15, 2)  NOT NULL CHECK (price >= 0),
    currency    VARCHAR(3)      NOT NULL DEFAULT 'USD',
    stock       INT             NOT NULL DEFAULT 0 CHECK (stock >= 0),
    category_id VARCHAR(100),
    image_url   VARCHAR(500),
    active      BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_products_sku      ON products (sku);
CREATE INDEX IF NOT EXISTS idx_products_category ON products (category_id);
CREATE INDEX IF NOT EXISTS idx_products_active   ON products (active);
CREATE INDEX IF NOT EXISTS idx_products_name_trgm ON products USING GIN (name gin_trgm_ops);

-- Orders
CREATE TABLE IF NOT EXISTS orders (
    id            UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_number  VARCHAR(50)     NOT NULL UNIQUE,
    customer_id   BIGINT          NOT NULL,
    customer_name VARCHAR(200)    NOT NULL,
    created_by    VARCHAR(100)    NOT NULL,
    order_date    TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status        VARCHAR(20)     NOT NULL DEFAULT 'PENDING'
                      CHECK (status IN ('PENDING', 'CONFIRMED', 'SHIPPED', 'DELIVERED', 'CANCELLED')),
    total_amount  DECIMAL(15, 2)  NOT NULL CHECK (total_amount >= 0),
    currency      VARCHAR(3)      NOT NULL DEFAULT 'USD',
    created_at    TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_orders_order_number  ON orders (order_number);
CREATE INDEX IF NOT EXISTS idx_orders_customer_id   ON orders (customer_id);
CREATE INDEX IF NOT EXISTS idx_orders_status        ON orders (status);
CREATE INDEX IF NOT EXISTS idx_orders_order_date    ON orders (order_date);

-- Order line items (snapshot data)
CREATE TABLE IF NOT EXISTS order_products (
    id           UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id     UUID           NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
    product_id   UUID           NOT NULL REFERENCES products (id) ON DELETE RESTRICT,
    product_name VARCHAR(200)   NOT NULL,
    quantity     INT            NOT NULL CHECK (quantity > 0),
    unit_price   DECIMAL(15, 2) NOT NULL CHECK (unit_price >= 0),
    subtotal     DECIMAL(15, 2) NOT NULL CHECK (subtotal >= 0)
);

CREATE INDEX IF NOT EXISTS idx_order_products_order_id   ON order_products (order_id);
CREATE INDEX IF NOT EXISTS idx_order_products_product_id ON order_products (product_id);
