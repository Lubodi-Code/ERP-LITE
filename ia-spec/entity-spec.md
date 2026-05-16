In erp-infrastructure/src/main/java/com/erp/infrastructure/persistence/jpa/entity/ 
create three JPA entity classes that EXACTLY match the PostgreSQL schema 
defined in db/postgresql/01-schema.sql. @db/mongodb/init-mongo.js @db/postgresql/01-schema.sql  @db/postgresql/02-data.sql   

GLOBAL RULES:
- Suffix all classes with Entity
- Use Lombok: @Data, @Builder, @NoArgsConstructor, @AllArgsConstructor
- Use @Entity, @Table, @Id, @Column with snake_case column names
- UUID ids with @GeneratedValue(strategy = GenerationType.UUID)
  (matches Postgres uuid_generate_v4() default — let DB generate IDs)
- Timestamps as Instant, map to TIMESTAMP columns
- Monetary fields as BigDecimal with precision=15, scale=2
- Add @PrePersist/@PreUpdate for createdAt/updatedAt automatic management
- application.yml uses jpa.hibernate.ddl-auto=validate, so column names, 
  types, lengths and constraints MUST match the existing schema exactly

=== 1. OrderEntity → table "orders" ===
Fields (snake_case in DB, camelCase in Java):
- id: UUID, PK
- orderNumber: String(50), NOT NULL, UNIQUE → order_number
- customerId: Long, NOT NULL → customer_id (BIGINT, ref to JSONPlaceholder)
- customerName: String(200), NOT NULL → customer_name
- createdBy: String(100), NOT NULL → created_by
- orderDate: Instant, NOT NULL → order_date
- status: String(20), NOT NULL, default "PENDING" 
  (DB has CHECK constraint: PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
- totalAmount: BigDecimal(15,2), NOT NULL → total_amount
- currency: String(3), NOT NULL, default "USD"
- createdAt: Instant, NOT NULL → created_at
- updatedAt: Instant, NOT NULL → updated_at

Relationship:
@OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true, 
  fetch=FetchType.LAZY)
List<OrderProductEntity> items = new ArrayList<>();

=== 2. ProductEntity → table "products" ===
Fields:
- id: UUID, PK
- sku: String(50), NOT NULL, UNIQUE
- name: String(200), NOT NULL
- description: String, columnDefinition="TEXT" (no length limit)
- price: BigDecimal(15,2), NOT NULL
- stock: int, NOT NULL, default 0
- categoryId: String(100) → category_id 
  (IMPORTANT: this is a logical reference to MongoDB CatalogDocument, 
   NOT a JPA relationship — keep it as plain String, no @ManyToOne)
- imageUrl: String(500) → image_url (S3 URL)
- active: boolean, NOT NULL, default true
- createdAt: Instant, NOT NULL → created_at
- updatedAt: Instant, NOT NULL → updated_at

=== 3. OrderProductEntity → table "order_products" ===
Fields:
- id: UUID, PK
- productName: String(200), NOT NULL → product_name 
  (SNAPSHOT at order creation — does NOT change if product name updates later)
- quantity: int, NOT NULL (CHECK > 0)
- unitPrice: BigDecimal(15,2), NOT NULL → unit_price 
  (SNAPSHOT — price at time of order)
- subtotal: BigDecimal(15,2), NOT NULL (= quantity * unit_price)

Relationships:
@ManyToOne(fetch=FetchType.LAZY)
@JoinColumn(name="order_id", nullable=false, 
  foreignKey=@ForeignKey(name="fk_order_products_order"))
OrderEntity order;  // ON DELETE CASCADE in DB

@ManyToOne(fetch=FetchType.LAZY)
@JoinColumn(name="product_id", nullable=false,
  foreignKey=@ForeignKey(name="fk_order_products_product"))
ProductEntity product;  // ON DELETE RESTRICT in DB

NOTES:
- Do NOT use @ManyToMany with @JoinTable — order_products is a real entity 
  with extra fields (quantity, unitPrice, subtotal), not a simple join table
- For Lombok with bidirectional relationships, exclude items/order from 
  @ToString and @EqualsAndHashCode to avoid infinite recursion:
  @ToString(exclude = "items")
  @EqualsAndHashCode(exclude = "items")