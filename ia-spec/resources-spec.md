Crea el mapeo de Documents spring data mongo basdo en las sigientes estructuras, ** Usa records para objetos embebidos y clases para Mapeos generales ** Te dejo la lista de colecciones con un par de ejemplos de data:

	-       items en catalogs debería ser un arreglo JSON embebido (no un String)
	-	Fechas como Instant.
	-	fee como BigDecimal.
	-	Uso de un enum para catalogType.
        -       Usa lombok

catalogs:

[
  {
    "_id": "catalog-product-categories",
    "active": true,
    "catalogType": "PRODUCT_CATEGORIES",
    "createdAt": {"$date": "2025-01-01T00:00:00.000Z"},
    "description": "Main product categories for the ERP system",
    "items": "[{id=cat-electronics, code=ELECTRONICS, value=Electronics, description=Electronic devices and accessories, displayOrder=1, metadata={icon=laptop, color=#2196F3}}, {id=cat-furniture, code=FURNITURE, value=Furniture, description=Office and home furniture, displayOrder=2, metadata={icon=chair, color=#4CAF50}}, {id=cat-accessories, code=ACCESSORIES, value=Accessories, description=Various accessories and peripherals, displayOrder=3, metadata={icon=backpack, color=#FF9800}}, {id=cat-stationery, code=STATIONERY, value=Stationery, description=Office supplies and stationery, displayOrder=4, metadata={icon=pencil, color=#9C27B0}}]",
    "name": "Product Categories",
    "updatedAt": {"$date": "2025-01-01T00:00:00.000Z"}
  },
  {
    "_id": "catalog-order-statuses",
    "active": true,
    "catalogType": "ORDER_STATUSES",
    "createdAt": {"$date": "2025-01-01T00:00:00.000Z"},
    "description": "Possible statuses for orders",
    "items": "[{id=status-pending, code=PENDING, value=Pending, description=Order created, awaiting confirmation, displayOrder=1, metadata={color=#FFC107, nextStatuses=[CONFIRMED, CANCELLED]}}, {id=status-confirmed, code=CONFIRMED, value=Confirmed, description=Order confirmed, ready for shipment, displayOrder=2, metadata={color=#2196F3, nextStatuses=[SHIPPED, CANCELLED]}}, {id=status-shipped, code=SHIPPED, value=Shipped, description=Order shipped, displayOrder=3, metadata={color=#FF9800, nextStatuses=[DELIVERED]}}, {id=status-delivered, code=DELIVERED, value=Delivered, description=Order delivered successfully, displayOrder=4, metadata={color=#4CAF50, nextStatuses=[]}}, {id=status-cancelled, code=CANCELLED, value=Cancelled, description=Order cancelled, displayOrder=5, metadata={color=#F44336, nextStatuses=[]}}]",
    "name": "Order Statuses",
    "updatedAt": {"$date": "2025-01-01T00:00:00.000Z"}
  },
  {
    "_id": "catalog-payment-methods",
    "active": true,
    "catalogType": "PAYMENT_METHODS",
    "createdAt": {"$date": "2025-01-01T00:00:00.000Z"},
    "description": "Available payment methods",
    "items": "[{id=pay-credit-card, code=CREDIT_CARD, value=Credit Card, description=Visa, Mastercard, American Express, displayOrder=1, metadata={icon=credit-card, fee=2.9}}, {id=pay-paypal, code=PAYPAL, value=PayPal, description=PayPal account, displayOrder=2, metadata={icon=paypal, fee=3.4}}, {id=pay-bank-transfer, code=BANK_TRANSFER, value=Bank Transfer, description=Direct bank transfer, displayOrder=3, metadata={icon=bank, fee=0}}, {id=pay-cash, code=CASH, value=Cash, description=Cash on delivery, displayOrder=4, metadata={icon=money, fee=0}}]",
    "name": "Payment Methods",
    "updatedAt": {"$date": "2025-01-01T00:00:00.000Z"}
  }
]
product_documents:

[
  {
    "_id": "11111111-1111-1111-1111-111111111111",
    "active": true,
    "categoryId": "cat-electronics",
    "categoryName": "Electronics",
    "createdAt": {"$date": "2025-01-01T00:00:00.000Z"},
    "currency": "USD",
    "description": "High-performance laptop with Intel i7, 16GB RAM, 512GB SSD",
    "imageUrl": null,
    "name": "Laptop Dell XPS 15",
    "price": 1499.99,
    "sku": "LAPTOP-001",
    "specifications": {
      "processor": "Intel Core i7-13700H",
      "ram": "16GB DDR5",
      "storage": "512GB NVMe SSD",
      "display": "15.6\" FHD",
      "weight": "2.0 kg"
    },
    "stock": 25,
    "tags": ["laptop", "dell", "xps", "intel", "portable"],
    "updatedAt": {"$date": "2025-01-01T00:00:00.000Z"}
  },
  {
    "_id": "22222222-2222-2222-2222-222222222222",
    "active": true,
    "categoryId": "cat-electronics",
    "categoryName": "Electronics",
    "createdAt": {"$date": "2025-01-01T00:00:00.000Z"},
    "currency": "USD",
    "description": "Apple M3 Pro chip, 18GB RAM, 512GB SSD",
    "imageUrl": null,
    "name": "MacBook Pro 14\"",
    "price": 1999.99,
    "sku": "LAPTOP-002",
    "specifications": {
      "processor": "Apple M3 Pro",
      "ram": "18GB Unified Memory",
      "storage": "512GB SSD",
      "display": "14.2\" Liquid Retina XDR",
      "weight": "1.6 kg"
    },
    "stock": 15,
    "tags": ["laptop", "apple", "macbook", "m3", "pro"],
    "updatedAt": {"$date": "2025-01-01T00:00:00.000Z"}
  }
]


audit_logs:

[
  {
    "_id": {"$oid": "6931e4170cf4a173c6b1ddf4"},
    "className": "CreateOrderUseCase",
    "endpoint": "/api/v1/orders",
    "errorMessage": null,
    "executionTimeMs": 127,
    "ipAddress": "127.0.0.1",
    "methodName": "execute",
    "success": true,
    "timestamp": {"$date": "2025-12-04T19:42:15.173Z"},
    "userId": "admin"
  }
]