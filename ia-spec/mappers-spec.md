In erp-infrastructure/src/main/java/com/erp/infrastructure/persistence/mapper/
create MapStruct mappers. Domain aggregates now have rehydrate() factory methods.

PRECONDITIONS (already done):
- Product.rehydrate(...), Order.rehydrate(...), OrderItem.rehydrate(...) exist in domain
- ProductEntity has: id, sku, name, description, price(BigDecimal), currency(String), 
  stock(int), categoryId, imageUrl, active, createdAt, updatedAt
- OrderEntity has: id, orderNumber, customerId(Long), customerName, createdBy, 
  orderDate, status(String), totalAmount(BigDecimal), currency, createdAt, updatedAt, 
  List<OrderProductEntity> items
- OrderProductEntity has: id, product(ProductEntity), productName, quantity(int), 
  unitPrice(BigDecimal), subtotal(BigDecimal) — NO currency field

=== 1. ProductMapper.java ===
@Mapper(componentModel = "spring")

Methods:
- Product toDomain(ProductEntity entity) — use Product.rehydrate(...)
- ProductEntity toEntity(Product product)

Default helpers needed:
- ProductId toProductId(UUID v) / UUID fromProductId(ProductId v)
- SKU toSku(String v) / String fromSku(SKU v)
- ProductName toName(String v) / String fromName(ProductName v)
- Stock toStock(int v) / int fromStock(Stock v)
- CategoryReference toCategory(String v) / String fromCategory(CategoryReference v)
- ProductImage toImage(String v) / String fromImage(ProductImage v)
- Money toMoney(BigDecimal amount, String currency)
- BigDecimal fromMoneyAmount(Money m) / String fromMoneyCurrency(Money m)
- AuditInfo toAudit(String createdBy, Instant createdAt, Instant updatedAt)

NOTE: ProductEntity has no createdBy — pass null for AuditInfo.createdBy in toDomain

=== 2. OrderItemMapper.java ===
@Mapper(componentModel = "spring")

Methods:
- OrderItem toDomain(OrderProductEntity e, @Context String currency)
  (currency comes from OrderEntity — needed to build Money for unitPrice/subtotal)
  Use OrderItem.rehydrate(...)
- OrderProductEntity toEntity(OrderItem item)

Default helpers:
- ProductId toProductId(UUID v) / UUID fromProductId(ProductId v)
- Quantity toQty(int v) / int fromQty(Quantity v)
- Money toMoney(BigDecimal amount, String currency)
- BigDecimal fromMoneyAmount(Money m)

=== 3. OrderMapper.java ===
@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})

Methods:
- Order toDomain(OrderEntity e) — use Order.rehydrate(...)
  Pass e.getCurrency() as @Context to OrderItemMapper when mapping items
- OrderEntity toEntity(Order order)

Explicit @Mapping needed:
- customerId ← customer.customerId.value
- customerName ← customer.customerName
- status ← status.value (String)
- totalAmount ← totalAmount.amount
- currency ← totalAmount.currency
- createdBy ← auditInfo.createdBy
- createdAt ← auditInfo.createdAt
- updatedAt ← auditInfo.updatedAt

Default helpers:
- OrderId toOrderId(UUID v) / UUID fromOrderId(OrderId v)
- OrderNumber toOrderNumber(String v) / String fromOrderNumber(OrderNumber v)
- Customer toCustomer(Long customerId, String customerName)
- OrderStatus toStatus(String v) / String fromStatus(OrderStatus v)
- Money toMoney(BigDecimal amount, String currency)
- AuditInfo toAudit(String createdBy, Instant createdAt, Instant updatedAt)

=== 4. CatalogMapper.java ===
@Mapper(componentModel = "spring")

Methods:
- Catalog toDomain(CatalogDocument doc)
- CatalogDocument toDocument(Catalog catalog)

Default helpers:
- CatalogItem toDomainItem(CatalogItemDocument doc)
- CatalogItemDocument toDocumentItem(CatalogItem item)
- Map<String,Object> toMetadataMap(CatalogItemMetadataDocument meta)

=== 5. ProductDocumentMapper.java ===
@Mapper(componentModel = "spring")

Methods:
- ProductDocument toDocument(Product product)
- Product toDomain(ProductDocument doc) — use Product.rehydrate(...)

Explicit mappings:
- id: UUID ↔ String (use helpers)
- price ← price.amount / currency ← price.currency
- categoryId ← category.categoryId
- imageUrl ← image.imageUrl (nullable)
- name ← name.value
- sku ← sku.value
- stock ← stock.value
- Ignore: categoryName, specifications, tags, createdAt, updatedAt 
  when going domain → document (set defaults with Instant.now())

After all mappers run:
./gradlew :erp-infrastructure:compileJava