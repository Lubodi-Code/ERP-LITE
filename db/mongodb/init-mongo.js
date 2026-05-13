// ============================================================
// ERP Lite — MongoDB initialisation script
// Target database: erp_catalog_db
// ============================================================

db = db.getSiblingDB('erp_catalog_db');

// ------------------------------------------------------------
// Collection: catalogs
// ------------------------------------------------------------
db.catalogs.drop();

db.catalogs.insertMany([
  {
    _id: 'PRODUCT_CATEGORIES',
    type: 'PRODUCT_CATEGORIES',
    description: 'Available product categories',
    items: [
      { code: 'cat-electronics', label: 'Electronics',  icon: 'laptop',   active: true },
      { code: 'cat-furniture',   label: 'Furniture',    icon: 'chair',    active: true },
      { code: 'cat-accessories', label: 'Accessories',  icon: 'headset',  active: true },
      { code: 'cat-stationery',  label: 'Stationery',   icon: 'pen',      active: true }
    ],
    createdAt: new Date('2024-01-01T00:00:00Z'),
    updatedAt: new Date('2024-01-01T00:00:00Z')
  },
  {
    _id: 'ORDER_STATUSES',
    type: 'ORDER_STATUSES',
    description: 'Valid order statuses and their allowed transitions',
    items: [
      { code: 'PENDING',   label: 'Pending',   nextStatuses: ['CONFIRMED', 'CANCELLED'], isFinal: false },
      { code: 'CONFIRMED', label: 'Confirmed', nextStatuses: ['SHIPPED',   'CANCELLED'], isFinal: false },
      { code: 'SHIPPED',   label: 'Shipped',   nextStatuses: ['DELIVERED'],              isFinal: false },
      { code: 'DELIVERED', label: 'Delivered', nextStatuses: [],                         isFinal: true  },
      { code: 'CANCELLED', label: 'Cancelled', nextStatuses: [],                         isFinal: true  }
    ],
    createdAt: new Date('2024-01-01T00:00:00Z'),
    updatedAt: new Date('2024-01-01T00:00:00Z')
  },
  {
    _id: 'PAYMENT_METHODS',
    type: 'PAYMENT_METHODS',
    description: 'Accepted payment methods and their processing fees',
    items: [
      { code: 'CREDIT_CARD',  label: 'Credit Card',   feePercent: 2.5,  active: true  },
      { code: 'DEBIT_CARD',   label: 'Debit Card',    feePercent: 1.5,  active: true  },
      { code: 'BANK_TRANSFER',label: 'Bank Transfer',  feePercent: 0.0,  active: true  },
      { code: 'PAYPAL',       label: 'PayPal',         feePercent: 3.4,  active: false }
    ],
    createdAt: new Date('2024-01-01T00:00:00Z'),
    updatedAt: new Date('2024-01-01T00:00:00Z')
  },
  {
    _id: 'SHIPPING_METHODS',
    type: 'SHIPPING_METHODS',
    description: 'Available shipping methods with cost and delivery estimates',
    items: [
      { code: 'STANDARD',  label: 'Standard Shipping', cost: 5.99,  estimatedDays: 7,  active: true },
      { code: 'EXPRESS',   label: 'Express Shipping',  cost: 14.99, estimatedDays: 3,  active: true },
      { code: 'OVERNIGHT', label: 'Overnight',         cost: 29.99, estimatedDays: 1,  active: true },
      { code: 'PICKUP',    label: 'Store Pickup',      cost: 0.00,  estimatedDays: 0,  active: true }
    ],
    createdAt: new Date('2024-01-01T00:00:00Z'),
    updatedAt: new Date('2024-01-01T00:00:00Z')
  },
  {
    _id: 'COUNTRIES',
    type: 'COUNTRIES',
    description: 'Supported countries for shipping and billing',
    items: [
      { code: 'US', label: 'United States',  flag: '🇺🇸', currency: 'USD', phonePrefix: '+1'   },
      { code: 'MX', label: 'Mexico',         flag: '🇲🇽', currency: 'MXN', phonePrefix: '+52'  },
      { code: 'CA', label: 'Canada',         flag: '🇨🇦', currency: 'CAD', phonePrefix: '+1'   },
      { code: 'UK', label: 'United Kingdom', flag: '🇬🇧', currency: 'GBP', phonePrefix: '+44'  },
      { code: 'DE', label: 'Germany',        flag: '🇩🇪', currency: 'EUR', phonePrefix: '+49'  }
    ],
    createdAt: new Date('2024-01-01T00:00:00Z'),
    updatedAt: new Date('2024-01-01T00:00:00Z')
  },
  {
    _id: 'CURRENCIES',
    type: 'CURRENCIES',
    description: 'Supported currencies',
    items: [
      { code: 'USD', label: 'US Dollar',        symbol: '$',  decimals: 2, active: true },
      { code: 'MXN', label: 'Mexican Peso',      symbol: '$',  decimals: 2, active: true },
      { code: 'EUR', label: 'Euro',              symbol: '€',  decimals: 2, active: true },
      { code: 'GBP', label: 'British Pound',     symbol: '£',  decimals: 2, active: true },
      { code: 'CAD', label: 'Canadian Dollar',   symbol: 'CA$',decimals: 2, active: true }
    ],
    createdAt: new Date('2024-01-01T00:00:00Z'),
    updatedAt: new Date('2024-01-01T00:00:00Z')
  }
]);

db.catalogs.createIndex({ type: 1 },            { unique: true });
db.catalogs.createIndex({ 'items.code': 1 });

// ------------------------------------------------------------
// Collection: product_documents  (denormalized read model)
// ------------------------------------------------------------
db.product_documents.drop();

db.product_documents.insertMany([
  {
    productId:   '11111111-1111-1111-1111-111111111111',
    sku:         'LAPTOP-001',
    name:        'ProBook Laptop 15"',
    description: 'High-performance laptop with Intel Core i7 and 16GB RAM',
    price:       { amount: 1299.99, currency: 'USD' },
    stock:       45,
    categoryId:  'cat-electronics',
    categoryLabel: 'Electronics',
    imageUrl:    'https://assets.erp-lite.com/products/laptop-001.jpg',
    active:      true,
    tags:        ['laptop', 'intel', 'i7', 'portable', 'work'],
    specifications: {
      processor:   'Intel Core i7-1255U',
      ram:         '16 GB DDR5',
      storage:     '512 GB NVMe SSD',
      display:     '15.6" FHD IPS',
      battery:     '65 Wh',
      weight:      '1.8 kg',
      os:          'Windows 11 Pro'
    },
    createdAt: new Date('2024-01-15T10:00:00Z'),
    updatedAt: new Date('2024-01-15T10:00:00Z')
  },
  {
    productId:   '22222222-2222-2222-2222-222222222222',
    sku:         'MOUSE-001',
    name:        'Wireless Ergonomic Mouse',
    description: 'Ergonomic wireless mouse with 2.4GHz connectivity and 1-year battery life',
    price:       { amount: 39.99, currency: 'USD' },
    stock:       120,
    categoryId:  'cat-electronics',
    categoryLabel: 'Electronics',
    imageUrl:    'https://assets.erp-lite.com/products/mouse-001.jpg',
    active:      true,
    tags:        ['mouse', 'wireless', 'ergonomic', 'office'],
    specifications: {
      connectivity: '2.4 GHz USB receiver',
      dpi:          '800 / 1200 / 1600',
      battery:      '1x AA (12 months)',
      buttons:      6,
      weight:       '101 g'
    },
    createdAt: new Date('2024-01-15T10:00:00Z'),
    updatedAt: new Date('2024-01-15T10:00:00Z')
  },
  {
    productId:   '33333333-3333-3333-3333-333333333333',
    sku:         'MONITOR-001',
    name:        '27" 4K UHD Monitor',
    description: '27-inch 4K Ultra HD IPS display with 144Hz refresh rate',
    price:       { amount: 499.99, currency: 'USD' },
    stock:       30,
    categoryId:  'cat-electronics',
    categoryLabel: 'Electronics',
    imageUrl:    'https://assets.erp-lite.com/products/monitor-001.jpg',
    active:      true,
    tags:        ['monitor', '4k', 'uhd', '144hz', 'ips', 'gaming'],
    specifications: {
      resolution:   '3840 x 2160 (4K UHD)',
      panel:        'IPS',
      refreshRate:  '144 Hz',
      responseTime: '1 ms (GtG)',
      ports:        ['HDMI 2.1 x2', 'DisplayPort 1.4', 'USB-C 90W'],
      vesa:         '100 x 100 mm'
    },
    createdAt: new Date('2024-01-15T10:00:00Z'),
    updatedAt: new Date('2024-01-15T10:00:00Z')
  },
  {
    productId:   '44444444-4444-4444-4444-444444444444',
    sku:         'KEYBOARD-001',
    name:        'Mechanical Keyboard TKL',
    description: 'Tenkeyless mechanical keyboard with Cherry MX Red switches and RGB backlight',
    price:       { amount: 129.99, currency: 'USD' },
    stock:       75,
    categoryId:  'cat-electronics',
    categoryLabel: 'Electronics',
    imageUrl:    'https://assets.erp-lite.com/products/keyboard-001.jpg',
    active:      true,
    tags:        ['keyboard', 'mechanical', 'tkl', 'rgb', 'cherry-mx', 'gaming'],
    specifications: {
      switches:     'Cherry MX Red (Linear)',
      layout:       'Tenkeyless (87-key)',
      backlight:    'Per-key RGB',
      connectivity: 'USB-A (detachable cable)',
      material:     'Aircraft-grade aluminium',
      rollover:     'N-Key Rollover'
    },
    createdAt: new Date('2024-01-15T10:00:00Z'),
    updatedAt: new Date('2024-01-15T10:00:00Z')
  },
  {
    productId:   '55555555-5555-5555-5555-555555555555',
    sku:         'CHAIR-001',
    name:        'Ergonomic Office Chair',
    description: 'Fully adjustable lumbar support chair with breathable mesh back',
    price:       { amount: 349.99, currency: 'USD' },
    stock:       20,
    categoryId:  'cat-furniture',
    categoryLabel: 'Furniture',
    imageUrl:    'https://assets.erp-lite.com/products/chair-001.jpg',
    active:      true,
    tags:        ['chair', 'ergonomic', 'office', 'mesh', 'lumbar'],
    specifications: {
      maxLoad:       '150 kg',
      seatHeight:    '43–53 cm (adjustable)',
      armrests:      '4D adjustable',
      material:      'Breathable mesh back, PU foam seat',
      warranty:      '5 years',
      certifications: ['BIFMA', 'CE']
    },
    createdAt: new Date('2024-01-20T09:00:00Z'),
    updatedAt: new Date('2024-01-20T09:00:00Z')
  },
  {
    productId:   '66666666-6666-6666-6666-666666666666',
    sku:         'DESK-001',
    name:        'Standing Desk 140x70cm',
    description: 'Electric height-adjustable standing desk with memory presets',
    price:       { amount: 599.99, currency: 'USD' },
    stock:       12,
    categoryId:  'cat-furniture',
    categoryLabel: 'Furniture',
    imageUrl:    'https://assets.erp-lite.com/products/desk-001.jpg',
    active:      true,
    tags:        ['desk', 'standing', 'electric', 'height-adjustable', 'office'],
    specifications: {
      dimensions:     '140 x 70 cm',
      heightRange:    '72–120 cm',
      motorType:      'Dual-motor electric',
      maxLoad:        '100 kg',
      memoryPresets:  4,
      noiseLevel:     '< 50 dB',
      material:       'Steel frame, MDF top'
    },
    createdAt: new Date('2024-01-20T09:00:00Z'),
    updatedAt: new Date('2024-01-20T09:00:00Z')
  },
  {
    productId:   '77777777-7777-7777-7777-777777777777',
    sku:         'SHELF-001',
    name:        'Modular Bookshelf 5-tier',
    description: 'Modern five-tier modular bookshelf in walnut finish',
    price:       { amount: 189.99, currency: 'USD' },
    stock:       25,
    categoryId:  'cat-furniture',
    categoryLabel: 'Furniture',
    imageUrl:    'https://assets.erp-lite.com/products/shelf-001.jpg',
    active:      true,
    tags:        ['shelf', 'bookshelf', 'modular', 'walnut', 'storage'],
    specifications: {
      tiers:        5,
      dimensions:   '80 x 30 x 175 cm',
      maxLoadPerTier: '20 kg',
      material:     'MDF with walnut veneer',
      assembly:     'Required'
    },
    createdAt: new Date('2024-01-20T09:00:00Z'),
    updatedAt: new Date('2024-01-20T09:00:00Z')
  },
  {
    productId:   '88888888-8888-8888-8888-888888888888',
    sku:         'CABINET-001',
    name:        'Filing Cabinet 3-drawer',
    description: 'Steel three-drawer lateral filing cabinet with lock',
    price:       { amount: 249.99, currency: 'USD' },
    stock:       18,
    categoryId:  'cat-furniture',
    categoryLabel: 'Furniture',
    imageUrl:    'https://assets.erp-lite.com/products/cabinet-001.jpg',
    active:      true,
    tags:        ['cabinet', 'filing', 'steel', 'lock', 'office'],
    specifications: {
      drawers:      3,
      dimensions:   '46 x 62 x 101 cm',
      maxLoad:      '40 kg per drawer',
      material:     'Cold-rolled steel',
      lock:         'Central lock (2 keys included)',
      color:        'Anthracite'
    },
    createdAt: new Date('2024-01-20T09:00:00Z'),
    updatedAt: new Date('2024-01-20T09:00:00Z')
  },
  {
    productId:   '99999999-9999-9999-9999-999999999999',
    sku:         'BAG-001',
    name:        'Laptop Backpack 30L',
    description: 'Water-resistant 30L backpack with padded laptop compartment up to 17"',
    price:       { amount: 79.99, currency: 'USD' },
    stock:       60,
    categoryId:  'cat-accessories',
    categoryLabel: 'Accessories',
    imageUrl:    'https://assets.erp-lite.com/products/bag-001.jpg',
    active:      true,
    tags:        ['backpack', 'bag', 'laptop', 'travel', 'waterproof'],
    specifications: {
      capacity:      '30 L',
      laptopSize:    'Up to 17"',
      material:      '600D Polyester (water-resistant)',
      pockets:       7,
      usbPort:       true,
      dimensions:    '32 x 20 x 48 cm'
    },
    createdAt: new Date('2024-02-01T09:00:00Z'),
    updatedAt: new Date('2024-02-01T09:00:00Z')
  },
  {
    productId:   'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    sku:         'HEADSET-001',
    name:        'Noise-Cancelling Headset',
    description: 'Over-ear wireless headset with active noise cancellation and 30h battery',
    price:       { amount: 199.99, currency: 'USD' },
    stock:       40,
    categoryId:  'cat-accessories',
    categoryLabel: 'Accessories',
    imageUrl:    'https://assets.erp-lite.com/products/headset-001.jpg',
    active:      true,
    tags:        ['headset', 'headphones', 'anc', 'wireless', 'noise-cancelling'],
    specifications: {
      type:         'Over-ear, closed-back',
      anc:          'Hybrid Active Noise Cancellation',
      battery:      '30 h (ANC on) / 40 h (ANC off)',
      connectivity: 'Bluetooth 5.2 / 3.5 mm jack',
      microphone:   'Built-in, retractable boom',
      weight:       '285 g'
    },
    createdAt: new Date('2024-02-01T09:00:00Z'),
    updatedAt: new Date('2024-02-01T09:00:00Z')
  }
]);

db.product_documents.createIndex({ productId: 1 }, { unique: true });
db.product_documents.createIndex({ sku: 1 },       { unique: true });
db.product_documents.createIndex({ categoryId: 1 });
db.product_documents.createIndex({ active: 1 });
db.product_documents.createIndex({ tags: 1 });
db.product_documents.createIndex({ 'price.amount': 1 });

// ------------------------------------------------------------
// Collection: audit_logs
// ------------------------------------------------------------
db.audit_logs.drop();

db.audit_logs.createIndex({ timestamp: -1 });
db.audit_logs.createIndex({ className: 1, methodName: 1 });
db.audit_logs.createIndex({ userId: 1 });
db.audit_logs.createIndex({ success: 1 });

db.audit_logs.insertOne({
  timestamp:   new Date('2025-02-14T16:00:00Z'),
  className:   'CreateOrderUseCase',
  methodName:  'execute',
  userId:      'sales01',
  success:     true,
  durationMs:  142,
  inputSummary: {
    customerId:   5,
    itemCount:    4,
    totalAmount:  214.97,
    currency:     'USD'
  },
  outputSummary: {
    orderId:     'b6b6b6b6-b6b6-b6b6-b6b6-b6b6b6b6b6b6',
    orderNumber: 'ORD-2025-006',
    status:      'PENDING'
  },
  host:   'app-node-01',
  traceId: 'trace-b6b6-2025'
});

// ------------------------------------------------------------
// Initialisation summary
// ------------------------------------------------------------
print('');
print('=== ERP Lite — MongoDB init complete ===');
print('catalogs:         ' + db.catalogs.countDocuments()          + ' documents');
print('product_documents:' + db.product_documents.countDocuments() + ' documents');
print('audit_logs:       ' + db.audit_logs.countDocuments()        + ' documents');
print('=========================================');
