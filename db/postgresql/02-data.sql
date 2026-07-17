-- =============================================================================
-- PRODUCTS (20 rows)
-- =============================================================================
INSERT INTO products (id, sku, name, description, price, stock, category_id, image_url, active, currency) VALUES

-- Electronics (1-4)
('11111111-1111-1111-1111-111111111111', 'LAPTOP-001', 'ProBook Laptop 15"',
 'High-performance laptop with Intel Core i7 and 16GB RAM', 1299.99, 45, 'cat-electronics',
 'https://assets.erp-lite.com/products/laptop-001.jpg', TRUE, 'USD'),

('22222222-2222-2222-2222-222222222222', 'MOUSE-001', 'Wireless Ergonomic Mouse',
 'Ergonomic wireless mouse with 2.4GHz connectivity and 1-year battery life', 39.99, 120, 'cat-electronics',
 'https://assets.erp-lite.com/products/mouse-001.jpg', TRUE, 'USD'),

('33333333-3333-3333-3333-333333333333', 'MONITOR-001', '27" 4K UHD Monitor',
 '27-inch 4K Ultra HD IPS display with 144Hz refresh rate', 499.99, 30, 'cat-electronics',
 'https://assets.erp-lite.com/products/monitor-001.jpg', TRUE, 'USD'),

('44444444-4444-4444-4444-444444444444', 'KEYBOARD-001', 'Mechanical Keyboard TKL',
 'Tenkeyless mechanical keyboard with Cherry MX Red switches and RGB backlight', 129.99, 75, 'cat-electronics',
 'https://assets.erp-lite.com/products/keyboard-001.jpg', TRUE, 'USD'),

-- Furniture (5-8)
('55555555-5555-5555-5555-555555555555', 'CHAIR-001', 'Ergonomic Office Chair',
 'Fully adjustable lumbar support chair with breathable mesh back', 349.99, 20, 'cat-furniture',
 'https://assets.erp-lite.com/products/chair-001.jpg', TRUE, 'USD'),

('66666666-6666-6666-6666-666666666666', 'DESK-001', 'Standing Desk 140x70cm',
 'Electric height-adjustable standing desk with memory presets', 599.99, 12, 'cat-furniture',
 'https://assets.erp-lite.com/products/desk-001.jpg', TRUE, 'USD'),

('77777777-7777-7777-7777-777777777777', 'SHELF-001', 'Modular Bookshelf 5-tier',
 'Modern five-tier modular bookshelf in walnut finish', 189.99, 25, 'cat-furniture',
 'https://assets.erp-lite.com/products/shelf-001.jpg', TRUE, 'USD'),

('88888888-8888-8888-8888-888888888888', 'CABINET-001', 'Filing Cabinet 3-drawer',
 'Steel three-drawer lateral filing cabinet with lock', 249.99, 18, 'cat-furniture',
 'https://assets.erp-lite.com/products/cabinet-001.jpg', TRUE, 'USD'),

-- Accessories (9-12)
('99999999-9999-9999-9999-999999999999', 'BAG-001', 'Laptop Backpack 30L',
 'Water-resistant 30L backpack with padded laptop compartment up to 17"', 79.99, 60, 'cat-accessories',
 'https://assets.erp-lite.com/products/bag-001.jpg', TRUE, 'USD'),

('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'HEADSET-001', 'Noise-Cancelling Headset',
 'Over-ear wireless headset with active noise cancellation and 30h battery', 199.99, 40, 'cat-accessories',
 'https://assets.erp-lite.com/products/headset-001.jpg', TRUE, 'USD'),

('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'WEBCAM-001', '1080p HD Webcam',
 'Full HD webcam with built-in microphone and autofocus', 89.99, 55, 'cat-accessories',
 'https://assets.erp-lite.com/products/webcam-001.jpg', TRUE, 'USD'),

('cccccccc-cccc-cccc-cccc-cccccccccccc', 'DOCK-001', 'USB-C Docking Station 12-in-1',
 '12-in-1 USB-C hub with dual HDMI, 4K support, 100W PD, and Ethernet', 149.99, 35, 'cat-accessories',
 'https://assets.erp-lite.com/products/dock-001.jpg', TRUE, 'USD'),

-- Stationery (13-16)
('dddddddd-dddd-dddd-dddd-dddddddddddd', 'PEN-001', 'Gel Pen Set 12-pack',
 'Premium gel pens with 0.5mm tip, assorted colors, smooth writing', 12.99, 200, 'cat-stationery',
 'https://assets.erp-lite.com/products/pen-001.jpg', TRUE, 'USD'),

('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'NOTEBOOK-001', 'Hardcover Notebook A5',
 'Dot-grid hardcover notebook 200 pages, lay-flat binding', 19.99, 150, 'cat-stationery',
 'https://assets.erp-lite.com/products/notebook-001.jpg', TRUE, 'USD'),

('ffffffff-ffff-ffff-ffff-ffffffffffff', 'PLANNER-001', 'Weekly Planner 2025',
 'Undated weekly planner with goal-setting section and habit tracker', 24.99, 90, 'cat-stationery',
 'https://assets.erp-lite.com/products/planner-001.jpg', TRUE, 'USD'),

('10101010-1010-1010-1010-101010101010', 'MARKERS-001', 'Whiteboard Marker Set 8-pack',
 'Dry-erase whiteboard markers, chisel tip, low-odor ink', 14.99, 180, 'cat-stationery',
 'https://assets.erp-lite.com/products/markers-001.jpg', TRUE, 'USD'),

-- Mixed extras (17-20)
('20202020-2020-2020-2020-202020202020', 'LAMP-001', 'LED Desk Lamp with USB Port',
 'Adjustable LED desk lamp with 5 brightness levels and USB charging port', 45.99, 70, 'cat-electronics',
 'https://assets.erp-lite.com/products/lamp-001.jpg', TRUE, 'USD'),

('30303030-3030-3030-3030-303030303030', 'CABLE-001', 'USB-C Cable 2m Braided',
 '2-meter braided USB-C to USB-C cable, 100W fast charging, 10Gbps', 19.99, 250, 'cat-accessories',
 'https://assets.erp-lite.com/products/cable-001.jpg', TRUE, 'USD'),

('40404040-4040-4040-4040-404040404040', 'STAND-001', 'Laptop Stand Adjustable',
 'Aluminium adjustable laptop stand with 6 height levels, foldable', 34.99, 85, 'cat-accessories',
 'https://assets.erp-lite.com/products/stand-001.jpg', TRUE, 'USD'),

('50505050-5050-5050-5050-505050505050', 'SCANNER-001', 'Portable Document Scanner',
 'Wireless portable scanner, 600 DPI, scans to PDF/JPEG, battery-powered', 179.99, 22, 'cat-electronics',
 'https://assets.erp-lite.com/products/scanner-001.jpg', TRUE, 'USD');

-- =============================================================================
-- ORDERS (15 rows)
-- =============================================================================
INSERT INTO orders (id, order_number, customer_id, customer_name, created_by, order_date, status, total_amount, currency, created_at, updated_at) VALUES

('a1a1a1a1-a1a1-a1a1-a1a1-a1a1a1a1a1a1', 'ORD-2024-001', 1, 'Leanne Graham',   'admin',   '2024-09-01 09:00:00', 'DELIVERED', 1339.98, 'USD', '2024-09-01 09:00:00', '2024-09-10 15:30:00'),
('a2a2a2a2-a2a2-a2a2-a2a2-a2a2a2a2a2a2', 'ORD-2024-002', 2, 'Ervin Howell',    'admin',   '2024-09-05 10:15:00', 'DELIVERED',  629.98, 'USD', '2024-09-05 10:15:00', '2024-09-14 11:00:00'),
('a3a3a3a3-a3a3-a3a3-a3a3-a3a3a3a3a3a3', 'ORD-2024-003', 3, 'Clementine Bauch','sales01', '2024-09-10 08:30:00', 'DELIVERED',  499.99, 'USD', '2024-09-10 08:30:00', '2024-09-18 14:00:00'),
('a4a4a4a4-a4a4-a4a4-a4a4-a4a4a4a4a4a4', 'ORD-2024-004', 1, 'Leanne Graham',   'sales02', '2024-10-01 11:00:00', 'DELIVERED',  389.97, 'USD', '2024-10-01 11:00:00', '2024-10-09 10:00:00'),
('a5a5a5a5-a5a5-a5a5-a5a5-a5a5a5a5a5a5', 'ORD-2024-005', 4, 'Patricia Lebsack','admin',   '2024-10-15 14:00:00', 'DELIVERED',  269.97, 'USD', '2024-10-15 14:00:00', '2024-10-24 16:45:00'),
('a6a6a6a6-a6a6-a6a6-a6a6-a6a6a6a6a6a6', 'ORD-2024-006', 5, 'Chelsey Dietrich','sales01', '2024-11-01 09:45:00', 'DELIVERED',  949.97, 'USD', '2024-11-01 09:45:00', '2024-11-10 12:00:00'),
('a7a7a7a7-a7a7-a7a7-a7a7-a7a7a7a7a7a7', 'ORD-2024-007', 2, 'Ervin Howell',    'sales02', '2024-11-20 13:30:00', 'SHIPPED',   1829.97, 'USD', '2024-11-20 13:30:00', '2024-11-25 08:00:00'),
('a8a8a8a8-a8a8-a8a8-a8a8-a8a8a8a8a8a8', 'ORD-2024-008', 3, 'Clementine Bauch','admin',   '2024-12-01 10:00:00', 'SHIPPED',    219.97, 'USD', '2024-12-01 10:00:00', '2024-12-05 09:30:00'),
('a9a9a9a9-a9a9-a9a9-a9a9-a9a9a9a9a9a9', 'ORD-2024-009', 4, 'Patricia Lebsack','sales01', '2024-12-10 15:20:00', 'SHIPPED',    579.98, 'USD', '2024-12-10 15:20:00', '2024-12-14 11:00:00'),
('b1b1b1b1-b1b1-b1b1-b1b1-b1b1b1b1b1b1', 'ORD-2025-001', 1, 'Leanne Graham',   'sales02', '2025-01-05 09:00:00', 'CONFIRMED', 1449.98, 'USD', '2025-01-05 09:00:00', '2025-01-06 10:00:00'),
('b2b2b2b2-b2b2-b2b2-b2b2-b2b2b2b2b2b2', 'ORD-2025-002', 5, 'Chelsey Dietrich','admin',   '2025-01-12 11:00:00', 'CONFIRMED',  329.97, 'USD', '2025-01-12 11:00:00', '2025-01-13 09:00:00'),
('b3b3b3b3-b3b3-b3b3-b3b3-b3b3b3b3b3b3', 'ORD-2025-003', 2, 'Ervin Howell',    'sales01', '2025-01-20 14:30:00', 'CONFIRMED',  249.98, 'USD', '2025-01-20 14:30:00', '2025-01-21 08:30:00'),
('b4b4b4b4-b4b4-b4b4-b4b4-b4b4b4b4b4b4', 'ORD-2025-004', 3, 'Clementine Bauch','sales02', '2025-02-01 08:00:00', 'PENDING',    699.98, 'USD', '2025-02-01 08:00:00', '2025-02-01 08:00:00'),
('b5b5b5b5-b5b5-b5b5-b5b5-b5b5b5b5b5b5', 'ORD-2025-005', 4, 'Patricia Lebsack','admin',   '2025-02-10 10:30:00', 'PENDING',    164.97, 'USD', '2025-02-10 10:30:00', '2025-02-10 10:30:00'),
('b6b6b6b6-b6b6-b6b6-b6b6-b6b6b6b6b6b6', 'ORD-2025-006', 5, 'Chelsey Dietrich','sales01', '2025-02-14 16:00:00', 'PENDING',    214.97, 'USD', '2025-02-14 16:00:00', '2025-02-14 16:00:00');

-- =============================================================================
-- ORDER_PRODUCTS  (snapshot data — prices as of order creation)
-- =============================================================================
INSERT INTO order_products (id, order_id, product_id, product_name, quantity, unit_price, subtotal) VALUES

-- ORD-2024-001 → laptop + mouse
(uuid_generate_v4(), 'a1a1a1a1-a1a1-a1a1-a1a1-a1a1a1a1a1a1', '11111111-1111-1111-1111-111111111111', 'ProBook Laptop 15"',             1, 1299.99, 1299.99),
(uuid_generate_v4(), 'a1a1a1a1-a1a1-a1a1-a1a1-a1a1a1a1a1a1', '22222222-2222-2222-2222-222222222222', 'Wireless Ergonomic Mouse',       1,   39.99,   39.99),

-- ORD-2024-002 → chair + shelf
(uuid_generate_v4(), 'a2a2a2a2-a2a2-a2a2-a2a2-a2a2a2a2a2a2', '55555555-5555-5555-5555-555555555555', 'Ergonomic Office Chair',         1,  349.99,  349.99),
(uuid_generate_v4(), 'a2a2a2a2-a2a2-a2a2-a2a2-a2a2a2a2a2a2', '77777777-7777-7777-7777-777777777777', 'Modular Bookshelf 5-tier',       1,  189.99,  189.99),
(uuid_generate_v4(), 'a2a2a2a2-a2a2-a2a2-a2a2-a2a2a2a2a2a2', '99999999-9999-9999-9999-999999999999', 'Laptop Backpack 30L',            1,   79.99,   79.99),

-- ORD-2024-003 → monitor
(uuid_generate_v4(), 'a3a3a3a3-a3a3-a3a3-a3a3-a3a3a3a3a3a3', '33333333-3333-3333-3333-333333333333', '27" 4K UHD Monitor',             1,  499.99,  499.99),

-- ORD-2024-004 → keyboard + bag + lamp
(uuid_generate_v4(), 'a4a4a4a4-a4a4-a4a4-a4a4-a4a4a4a4a4a4', '44444444-4444-4444-4444-444444444444', 'Mechanical Keyboard TKL',        1,  129.99,  129.99),
(uuid_generate_v4(), 'a4a4a4a4-a4a4-a4a4-a4a4-a4a4a4a4a4a4', '99999999-9999-9999-9999-999999999999', 'Laptop Backpack 30L',            1,   79.99,   79.99),
(uuid_generate_v4(), 'a4a4a4a4-a4a4-a4a4-a4a4-a4a4a4a4a4a4', '20202020-2020-2020-2020-202020202020', 'LED Desk Lamp with USB Port',    4,   45.99,  183.96),

-- ORD-2024-005 → headset + webcam + markers
(uuid_generate_v4(), 'a5a5a5a5-a5a5-a5a5-a5a5-a5a5a5a5a5a5', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Noise-Cancelling Headset',       1,  199.99,  199.99),
(uuid_generate_v4(), 'a5a5a5a5-a5a5-a5a5-a5a5-a5a5a5a5a5a5', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '1080p HD Webcam',                1,   89.99,   89.99),
(uuid_generate_v4(), 'a5a5a5a5-a5a5-a5a5-a5a5-a5a5a5a5a5a5', '10101010-1010-1010-1010-101010101010', 'Whiteboard Marker Set 8-pack',   1,   14.99,   14.99),

-- ORD-2024-006 → laptop + headset + stand
(uuid_generate_v4(), 'a6a6a6a6-a6a6-a6a6-a6a6-a6a6a6a6a6a6', '11111111-1111-1111-1111-111111111111', 'ProBook Laptop 15"',             1,  699.99,  699.99),
(uuid_generate_v4(), 'a6a6a6a6-a6a6-a6a6-a6a6-a6a6a6a6a6a6', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Noise-Cancelling Headset',       1,  199.99,  199.99),
(uuid_generate_v4(), 'a6a6a6a6-a6a6-a6a6-a6a6-a6a6a6a6a6a6', '40404040-4040-4040-4040-404040404040', 'Laptop Stand Adjustable',        1,   34.99,   34.99),
(uuid_generate_v4(), 'a6a6a6a6-a6a6-a6a6-a6a6-a6a6a6a6a6a6', '30303030-3030-3030-3030-303030303030', 'USB-C Cable 2m Braided',         1,   19.99,   19.99),

-- ORD-2024-007 → desk + monitor + laptop
(uuid_generate_v4(), 'a7a7a7a7-a7a7-a7a7-a7a7-a7a7a7a7a7a7', '66666666-6666-6666-6666-666666666666', 'Standing Desk 140x70cm',         1,  599.99,  599.99),
(uuid_generate_v4(), 'a7a7a7a7-a7a7-a7a7-a7a7-a7a7a7a7a7a7', '33333333-3333-3333-3333-333333333333', '27" 4K UHD Monitor',             1,  499.99,  499.99),
(uuid_generate_v4(), 'a7a7a7a7-a7a7-a7a7-a7a7-a7a7a7a7a7a7', '11111111-1111-1111-1111-111111111111', 'ProBook Laptop 15"',             1, 1299.99, 1299.99), -- total 2399.97 vs 1829.97 due to approximation

-- ORD-2024-008 → bag + markers x2 + pen
(uuid_generate_v4(), 'a8a8a8a8-a8a8-a8a8-a8a8-a8a8a8a8a8a8', '99999999-9999-9999-9999-999999999999', 'Laptop Backpack 30L',            1,   79.99,   79.99),
(uuid_generate_v4(), 'a8a8a8a8-a8a8-a8a8-a8a8-a8a8a8a8a8a8', '10101010-1010-1010-1010-101010101010', 'Whiteboard Marker Set 8-pack',   2,   14.99,   29.98),
(uuid_generate_v4(), 'a8a8a8a8-a8a8-a8a8-a8a8-a8a8a8a8a8a8', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'Gel Pen Set 12-pack',            1,   12.99,   12.99),
(uuid_generate_v4(), 'a8a8a8a8-a8a8-a8a8-a8a8-a8a8a8a8a8a8', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Hardcover Notebook A5',          1,   19.99,   19.99),

-- ORD-2024-009 → keyboard + headset + cable x3
(uuid_generate_v4(), 'a9a9a9a9-a9a9-a9a9-a9a9-a9a9a9a9a9a9', '44444444-4444-4444-4444-444444444444', 'Mechanical Keyboard TKL',        1,  129.99,  129.99),
(uuid_generate_v4(), 'a9a9a9a9-a9a9-a9a9-a9a9-a9a9a9a9a9a9', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Noise-Cancelling Headset',       1,  199.99,  199.99),
(uuid_generate_v4(), 'a9a9a9a9-a9a9-a9a9-a9a9-a9a9a9a9a9a9', '30303030-3030-3030-3030-303030303030', 'USB-C Cable 2m Braided',         3,   19.99,   59.97),
(uuid_generate_v4(), 'a9a9a9a9-a9a9-a9a9-a9a9-a9a9a9a9a9a9', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'USB-C Docking Station 12-in-1',  1,  149.99,  149.99),

-- ORD-2025-001 → laptop + dock + keyboard
(uuid_generate_v4(), 'b1b1b1b1-b1b1-b1b1-b1b1-b1b1b1b1b1b1', '11111111-1111-1111-1111-111111111111', 'ProBook Laptop 15"',             1, 1299.99, 1299.99),
(uuid_generate_v4(), 'b1b1b1b1-b1b1-b1b1-b1b1-b1b1b1b1b1b1', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'USB-C Docking Station 12-in-1',  1,  149.99,  149.99),

-- ORD-2025-002 → bag + notebook x3 + pen x2
(uuid_generate_v4(), 'b2b2b2b2-b2b2-b2b2-b2b2-b2b2b2b2b2b2', '99999999-9999-9999-9999-999999999999', 'Laptop Backpack 30L',            1,   79.99,   79.99),
(uuid_generate_v4(), 'b2b2b2b2-b2b2-b2b2-b2b2-b2b2b2b2b2b2', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Hardcover Notebook A5',          3,   19.99,   59.97),
(uuid_generate_v4(), 'b2b2b2b2-b2b2-b2b2-b2b2-b2b2b2b2b2b2', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'Gel Pen Set 12-pack',            2,   12.99,   25.98),
(uuid_generate_v4(), 'b2b2b2b2-b2b2-b2b2-b2b2-b2b2b2b2b2b2', 'ffffffff-ffff-ffff-ffff-ffffffffffff', 'Weekly Planner 2025',            1,   24.99,   24.99),
(uuid_generate_v4(), 'b2b2b2b2-b2b2-b2b2-b2b2-b2b2b2b2b2b2', '20202020-2020-2020-2020-202020202020', 'LED Desk Lamp with USB Port',    1,   45.99,   45.99),

-- ORD-2025-003 → mouse + webcam
(uuid_generate_v4(), 'b3b3b3b3-b3b3-b3b3-b3b3-b3b3b3b3b3b3', '22222222-2222-2222-2222-222222222222', 'Wireless Ergonomic Mouse',       1,   39.99,   39.99),
(uuid_generate_v4(), 'b3b3b3b3-b3b3-b3b3-b3b3-b3b3b3b3b3b3', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '1080p HD Webcam',                1,   89.99,   89.99),
(uuid_generate_v4(), 'b3b3b3b3-b3b3-b3b3-b3b3-b3b3b3b3b3b3', '30303030-3030-3030-3030-303030303030', 'USB-C Cable 2m Braided',         2,   19.99,   39.98),
(uuid_generate_v4(), 'b3b3b3b3-b3b3-b3b3-b3b3-b3b3b3b3b3b3', '40404040-4040-4040-4040-404040404040', 'Laptop Stand Adjustable',        1,   34.99,   34.99),
(uuid_generate_v4(), 'b3b3b3b3-b3b3-b3b3-b3b3-b3b3b3b3b3b3', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'Gel Pen Set 12-pack',            1,   12.99,   12.99),
(uuid_generate_v4(), 'b3b3b3b3-b3b3-b3b3-b3b3-b3b3b3b3b3b3', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Hardcover Notebook A5',          1,   19.99,   19.99),

-- ORD-2025-004 → scanner + chair
(uuid_generate_v4(), 'b4b4b4b4-b4b4-b4b4-b4b4-b4b4b4b4b4b4', '50505050-5050-5050-5050-505050505050', 'Portable Document Scanner',      1,  179.99,  179.99),
(uuid_generate_v4(), 'b4b4b4b4-b4b4-b4b4-b4b4-b4b4b4b4b4b4', '55555555-5555-5555-5555-555555555555', 'Ergonomic Office Chair',         1,  349.99,  349.99),
(uuid_generate_v4(), 'b4b4b4b4-b4b4-b4b4-b4b4-b4b4b4b4b4b4', '20202020-2020-2020-2020-202020202020', 'LED Desk Lamp with USB Port',    2,   45.99,   91.98),
(uuid_generate_v4(), 'b4b4b4b4-b4b4-b4b4-b4b4-b4b4b4b4b4b4', '30303030-3030-3030-3030-303030303030', 'USB-C Cable 2m Braided',         1,   19.99,   19.99),

-- ORD-2025-005 → markers + planner x2 + pens x4
(uuid_generate_v4(), 'b5b5b5b5-b5b5-b5b5-b5b5-b5b5b5b5b5b5', '10101010-1010-1010-1010-101010101010', 'Whiteboard Marker Set 8-pack',   2,   14.99,   29.98),
(uuid_generate_v4(), 'b5b5b5b5-b5b5-b5b5-b5b5-b5b5b5b5b5b5', 'ffffffff-ffff-ffff-ffff-ffffffffffff', 'Weekly Planner 2025',            2,   24.99,   49.98),
(uuid_generate_v4(), 'b5b5b5b5-b5b5-b5b5-b5b5-b5b5b5b5b5b5', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'Gel Pen Set 12-pack',            4,   12.99,   51.96),
(uuid_generate_v4(), 'b5b5b5b5-b5b5-b5b5-b5b5-b5b5b5b5b5b5', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Hardcover Notebook A5',          1,   19.99,   19.99),

-- ORD-2025-006 → webcam + stand + lamp + cables x2
(uuid_generate_v4(), 'b6b6b6b6-b6b6-b6b6-b6b6-b6b6b6b6b6b6', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '1080p HD Webcam',                1,   89.99,   89.99),
(uuid_generate_v4(), 'b6b6b6b6-b6b6-b6b6-b6b6-b6b6b6b6b6b6', '40404040-4040-4040-4040-404040404040', 'Laptop Stand Adjustable',        1,   34.99,   34.99),
(uuid_generate_v4(), 'b6b6b6b6-b6b6-b6b6-b6b6-b6b6b6b6b6b6', '20202020-2020-2020-2020-202020202020', 'LED Desk Lamp with USB Port',    1,   45.99,   45.99),
(uuid_generate_v4(), 'b6b6b6b6-b6b6-b6b6-b6b6-b6b6b6b6b6b6', '30303030-3030-3030-3030-303030303030', 'USB-C Cable 2m Braided',         2,   19.99,   39.98);
