# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Node / npm Security Rules

These rules are mandatory. Do not bypass them under any circumstance.

### NEVER use `npm install`
Use `npm ci` instead. `npm install` can silently mutate `package-lock.json`, pull unlocked transitive upgrades, and has been the entry point for recent supply-chain attacks on this project.

```bash
# FORBIDDEN
npm install
npm install <package>
npm i

# REQUIRED alternatives
npm ci                        # reproducible install from lock file
npm ci --ignore-scripts       # preferred — also disables lifecycle scripts
npm install --ignore-scripts  # only if adding a new dep; review lock diff before committing
```

### Always disable lifecycle scripts when installing
`postinstall`, `preinstall`, and `install` hooks in `node_modules` are a common vector for hidden malicious code. Pass `--ignore-scripts` on every install.

```bash
npm ci --ignore-scripts
```

If a package genuinely requires its postinstall script (e.g. native bindings), confirm the script source manually in `node_modules/<pkg>/package.json` before re-running without the flag.

### Audit before running Node scripts
Before executing any `node <script>` or `npm run <task>` on unfamiliar code:
1. Open the script file and read it — look for `eval()`, `Function()`, `child_process.exec`, `spawn`, or base64-encoded strings.
2. Run `npm audit --audit-level=high` and resolve HIGH/CRITICAL findings before proceeding.
3. Never pipe remote content into Node: `curl ... | node -` is forbidden.

### Lock-file integrity check
After any dependency change, run:
```bash
npm audit
git diff package-lock.json   # review every added/changed entry before committing
```

Commit `package-lock.json` alongside `package.json` in the same commit. Never commit one without the other.

---

## Build & Run Commands

```bash
# Build
./gradlew build

# Run
./gradlew bootRun

# Run tests
./gradlew test

# Run a single test class
./gradlew test --tests "com.example.demo.SomeTest"
```

Stack: Spring Boot 4.0.6, Java 26, Gradle. Dependencies include Spring Data MongoDB, JPA (PostgreSQL), Redis, RabbitMQ (AMQP), and Lombok.

## Domain Architecture

This is a **DDD (Domain-Driven Design)** project. All business logic lives exclusively in `src/main/java/com/example/demo/domain/`. There are no application, infrastructure, or presentation layers yet.

### Base abstractions (`domain/common`)

- **`Entity<ID>`** — holds `private final ID id`, validates non-null in constructor, provides `equals`/`hashCode` by identity. Subclasses call `super(id)` and omit their own `id` field.
- **`AggregateRoot<ID>`** — does NOT extend `Entity`. Manages a `List<DomainEvent>` via `registerEvent()` / `pullDomainEvents()`. Aggregate roots declare their own `id` field.
- **`DomainEvent`** — marker interface for domain events.

### Bounded contexts

| Package | Root | Notes |
|---|---|---|
| `domain/product` | `Product extends AggregateRoot<ProductId>` | Factory method `Product.create(...)`, fires `ProductCreated`, `StockChanged`, `ProductUpdated`, `ProductDeactivated` |
| `domain/order` | `Order extends AggregateRoot<OrderId>` | Factory method `Order.create(...)`, enforces status transitions via `OrderStatus.canTransitionTo()`, fires order lifecycle events |
| `domain/catalog` | `Catalog extends AggregateRoot<String>` | Groups `CatalogItem` entries by `CatalogType` enum |

### Shared value objects (`domain/shared`)

- **`Money`** — record, enforces non-negative amount, same-currency arithmetic, scale 2.
- **`Quantity`**, **`AuditInfo`**, **`Email`**, **`CustomerId`** — all records with inline validation.

### ID types pattern

Each aggregate/entity has its own typed ID (e.g., `ProductId`, `OrderId`, `OrderItemId`). These wrap a `UUID` and expose a static `generate()` factory.

### Value object / Entity patterns

- Value objects are Java **records** with compact constructor validation.
- `OrderStatus` is a **record** (not enum) with static factories (`pending()`, `confirmed()`, etc.) and a `canTransitionTo()` state machine.
- `CatalogType` is an **enum** with `fromCode(String)` and `isValid(String)` helpers.

### Lombok conventions

- Aggregates and entities use `@Getter` + manual constructor (never `@AllArgsConstructor` on classes extending `Entity`/`AggregateRoot` — Lombok-generated constructors cannot call `super(id)` with validation).
- `@NoArgsConstructor(access = PROTECTED)` is used on aggregates for ORM/framework deserialization only.
- **Do not use `@EqualsAndHashCode` on `Entity` subclasses** — `Entity` already provides identity-based equals/hashCode.

### Fail-fast validation rule

All domain constructors and factory methods throw `IllegalArgumentException` for invalid input. No nulls, no blank strings on required fields. Validate at the top of every constructor before assigning fields.

# AGGREGATE ROOTS
# ----------------------------------------------------------------------------

[aggregates.Order]
type = "AggregateRoot"
extends = "AggregateRoot<OrderId>"
lombok = ["@Getter", "@NoArgsConstructor(access=PROTECTED)"]

  [aggregates.Order.properties]
  id = {type="OrderId", valid="NotNull", mut=false}
  orderNumber = {type="OrderNumber", valid="NotNull+Unique", mut=false}
  customer = {type="Customer", valid="NotNull", mut=false, comp="CustomerId+String"}
  status = {type="OrderStatus", valid="NotNull+ValidTransitions", mut=true}
  items = {type="List<OrderItem>", valid="NotEmpty+MinSize:1", mut=true}
  totalAmount = {type="Money", valid="NotNull+Calculated", mut=true, note="Sum of all item subtotals"}
  auditInfo = {type="AuditInfo", valid="NotNull", mut=true}

  [aggregates.Order.methods]
  factory = "create(OrderNumber, Customer, List<OrderItem>, String createdBy)"
  business = ["confirm()", "ship()", "deliver()", "cancel(String reason)", "addItem(OrderItem)", "removeItem(OrderItem)", "calculateTotal()"]
  validation = ["validateItems()", "validateTransition(OrderStatus)"]

[aggregates.Product]
type = "AggregateRoot"
extends = "AggregateRoot<ProductId>"
lombok = ["@Getter", "@NoArgsConstructor(access=PROTECTED)"]

  [aggregates.Product.properties]
  id = {type="ProductId", valid="NotNull", mut=false}
  sku = {type="SKU", valid="NotNull+Unique+ImmutableAfterCreation", mut=false}
  name = {type="ProductName", valid="NotNull+MinLength:3", mut=true}
  description = {type="String", valid="Optional", mut=true}
  price = {type="Money", valid="NotNull+>0", mut=true}
  stock = {type="Stock", valid="NotNull+>=0", mut=true}
  category = {type="CategoryReference", valid="NotNull", mut=true}
  image = {type="ProductImage", valid="Optional+ValidURL", mut=true}
  active = {type="boolean", valid="NotNull+Default:true", mut=true}
  auditInfo = {type="AuditInfo", valid="NotNull", mut=true}

  [aggregates.Product.methods]
  factory = "create(SKU, ProductName, String description, Money price, Stock stock, CategoryReference category, ProductImage image, String createdBy)"
  business = ["update(ProductName, String description, Money price, CategoryReference, ProductImage)", "incrementStock(int quantity, String reason)", "decrementStock(int quantity, String reason)", "changePrice(Money newPrice)", "deactivate()", "activate()", "hasAvailableStock(int requiredQuantity)"]

# ----------------------------------------------------------------------------
# ENTITIES
# ----------------------------------------------------------------------------

[entities.OrderItem]
type = "Entity"
extends = "Entity<OrderItemId>"
lombok = ["@Getter", "@NoArgsConstructor(access=PROTECTED)", "@AllArgsConstructor(access=PRIVATE)"]

  [entities.OrderItem.properties]
  id = {type="OrderItemId", valid="NotNull", mut=false}
  productReference = {type="ProductId", valid="NotNull", mut=false}
  productName = {type="String", valid="NotNull+NotBlank", mut=false, note="Snapshot at order creation"}
  quantity = {type="Quantity", valid="NotNull+>0", mut=false}
  unitPrice = {type="Money", valid="NotNull+>0", mut=false, note="Snapshot at order creation"}
  subtotal = {type="Money", valid="NotNull+Calculated", mut=false, note="quantity * unitPrice"}

  [entities.OrderItem.methods]
  factory = "from(Product product, Quantity quantity)"
  business = ["calculateSubtotal()"]

# ----------------------------------------------------------------------------
# VALUE OBJECTS - IDENTIFIERS
# ----------------------------------------------------------------------------

[valueObjects.OrderId]
type = "record"
props = {value = {type="UUID", valid="NotNull"}}
factory = ["of(UUID value)", "generate()"]
note = "Unique identifier for Order aggregate"

[valueObjects.OrderItemId]
type = "record"
props = {value = {type="UUID", valid="NotNull"}}
factory = ["of(UUID value)", "generate()"]
note = "Unique identifier for OrderItem entity"

[valueObjects.ProductId]
type = "record"
props = {value = {type="UUID", valid="NotNull"}}
factory = ["of(UUID value)", "generate()"]
note = "Unique identifier for Product aggregate"

[valueObjects.CustomerId]
type = "record"
props = {value = {type="Long", valid="NotNull+>0"}}
factory = ["of(Long value)"]
note = "Reference to external customer system (JSONPlaceholder)"

# ----------------------------------------------------------------------------
# VALUE OBJECTS - BUSINESS (COMPOSITE)
# ----------------------------------------------------------------------------

[valueObjects.Money]
type = "record"
  [valueObjects.Money.props]
  amount = {type="BigDecimal", valid="NotNull+>=0"}
  currency = {type="Currency", valid="NotNull"}
factory = ["of(BigDecimal amount, Currency currency)", "of(double amount, Currency currency)"]
methods = ["add(Money other)", "subtract(Money other)", "multiply(int multiplier)", "multiply(Quantity quantity)"]
note = "Immutable monetary value with currency"

[valueObjects.Customer]
type = "record"
  [valueObjects.Customer.props]
  customerId = {type="CustomerId", valid="NotNull"}
  customerName = {type="String", valid="NotNull+NotBlank"}
factory = ["of(CustomerId customerId, String customerName)"]
note = "Customer reference with basic info"

[valueObjects.AuditInfo]
type = "record"
  [valueObjects.AuditInfo.props]
  createdBy = {type="String", valid="NotNull+NotBlank"}
  createdAt = {type="Instant", valid="NotNull"}
  updatedAt = {type="Instant", valid="NotNull"}
factory = ["create(String createdBy, Instant timestamp)"]
methods = ["updateTimestamp()"]
note = "Audit information for aggregates"

# ----------------------------------------------------------------------------
# VALUE OBJECTS - BUSINESS (SIMPLE)
# ----------------------------------------------------------------------------

[valueObjects.OrderNumber]
type = "record"
props = {value = {type="String", valid="NotNull+Pattern:ORD-\\d{4}-\\d{3}+Unique"}}
factory = ["of(String value)", "generate()"]
note = "Unique order number. Pattern: ORD-2025-001"

[valueObjects.OrderStatus]
type = "record"
props = {value = {type="String", valid="NotNull+Enum:PENDING|CONFIRMED|SHIPPED|DELIVERED|CANCELLED"}}
factory = ["of(String value)", "pending()", "confirmed()", "shipped()", "delivered()", "cancelled()"]
methods = ["canTransitionTo(OrderStatus nextStatus)", "isPending()", "isConfirmed()", "isShipped()", "isDelivered()", "isCancelled()", "isFinalState()"]
note = "Order state with valid transitions. DELIVERED and CANCELLED are final states"

[valueObjects.SKU]
type = "record"
props = {value = {type="String", valid="NotNull+Pattern:[A-Z]+-\\d{3}+Unique"}}
factory = ["of(String value)"]
note = "Stock Keeping Unit. Unique and immutable. Pattern: LAPTOP-001"

[valueObjects.ProductName]
type = "record"
props = {value = {type="String", valid="NotNull+MinLength:3+MaxLength:200"}}
factory = ["of(String value)"]
note = "Product name"

[valueObjects.Stock]
type = "record"
props = {value = {type="Integer", valid="NotNull+>=0"}}
factory = ["of(int value)", "zero()"]
methods = ["increment(int quantity)", "decrement(int quantity)", "hasAvailable(int required)"]
note = "Product stock quantity. Cannot be negative"

[valueObjects.CategoryReference]
type = "record"
props = {categoryId = {type="String", valid="NotNull"}}
factory = ["of(String categoryId)"]
note = "Reference to Catalog in MongoDB. Example: cat-electronics"

[valueObjects.ProductImage]
type = "record"
props = {imageUrl = {type="String", valid="NotNull+ValidURL"}}
factory = ["of(String imageUrl)"]
methods = ["getFullUrl()", "getFileName()"]
note = "Product image URL stored in AWS S3"

[valueObjects.Quantity]
type = "record"
props = {value = {type="Integer", valid="NotNull+>0"}}
factory = ["of(int value)"]
note = "Quantity of items in order. Must be greater than 0"

[valueObjects.Email]
type = "record"
props = {value = {type="String", valid="NotNull+EmailPattern"}}
factory = ["of(String value)"]
note = "Email address for notifications"

# ----------------------------------------------------------------------------
# DOMAIN EVENTS - ORDER
# ----------------------------------------------------------------------------

[events.OrderCreated]
type = "record"
props = {orderId="OrderId", customerId="CustomerId", customerName="String", totalAmount="Money", timestamp="Instant"}
note = "Emitted when a new order is created"

[events.OrderConfirmed]
type = "record"
props = {orderId="OrderId", timestamp="Instant"}
note = "Emitted when order transitions PENDING -> CONFIRMED. TRIGGERS stock decrement"

[events.OrderShipped]
type = "record"
props = {orderId="OrderId", timestamp="Instant"}
note = "Emitted when order transitions CONFIRMED -> SHIPPED"

[events.OrderDelivered]
type = "record"
props = {orderId="OrderId", timestamp="Instant"}
note = "Emitted when order transitions SHIPPED -> DELIVERED. Final state"

[events.OrderCancelled]
type = "record"
props = {orderId="OrderId", reason="String", timestamp="Instant"}
note = "Emitted when order is cancelled. If was CONFIRMED, stock must be released"

# ----------------------------------------------------------------------------
# DOMAIN EVENTS - PRODUCT
# ----------------------------------------------------------------------------

[events.ProductCreated]
type = "record"
props = {productId="ProductId", sku="SKU", name="ProductName", price="Money", timestamp="Instant"}
note = "Emitted when a new product is created. TRIGGERS sync to MongoDB (CQRS)"

[events.ProductUpdated]
type = "record"
props = {productId="ProductId", timestamp="Instant"}
note = "Emitted when product info is updated. TRIGGERS sync to MongoDB"

[events.StockChanged]
type = "record"
props = {productId="ProductId", oldStock="Integer", newStock="Integer", reason="String", timestamp="Instant"}
note = "Emitted when stock changes (increment or decrement). TRIGGERS sync to MongoDB"

[events.ProductDeactivated]
type = "record"
props = {productId="ProductId", timestamp="Instant"}
note = "Emitted when product is deactivated. TRIGGERS sync to MongoDB"

# ============================================================================
# IMPLEMENTATION NOTES
# ============================================================================
# 
# Package Structure:
#   domain/
#   ├── common/
#   │   ├── AggregateRoot.java (abstract class with domain events list)
#   │   ├── Entity.java (abstract class with ID)
#   │   └── DomainEvent.java (marker interface)
#   ├── order/
#   │   ├── Order.java (AR)
#   │   ├── OrderItem.java (Entity)
#   │   ├── OrderId.java (VO record)
#   │   ├── OrderNumber.java (VO record)
#   │   ├── OrderStatus.java (VO record)
#   │   ├── Customer.java (VO record)
#   │   └── events/
#   │       ├── OrderCreated.java (record)
#   │       ├── OrderConfirmed.java (record)
#   │       ├── OrderShipped.java (record)
#   │       ├── OrderDelivered.java (record)
#   │       └── OrderCancelled.java (record)
#   ├── product/
#   │   ├── Product.java (AR)
#   │   ├── ProductId.java (VO record)
#   │   ├── SKU.java (VO record)
#   │   ├── ProductName.java (VO record)
#   │   ├── Stock.java (VO record)
#   │   ├── CategoryReference.java (VO record)
#   │   ├── ProductImage.java (VO record)
#   │   └── events/
#   │       ├── ProductCreated.java (record)
#   │       ├── ProductUpdated.java (record)
#   │       ├── StockChanged.java (record)
#   │       └── ProductDeactivated.java (record)
#   └── shared/
#       ├── Money.java (VO record)
#       ├── Quantity.java (VO record)
#       ├── Email.java (VO record)
#       ├── CustomerId.java (VO record)
#       └── AuditInfo.java (VO record)
#
# Implementation Rules:
# 1. Use Java 21+ records for all Value Objects (unless complex behavior needed)
# 2. Lombok annotations ONLY on Aggregate Roots and Entities (not on records)
# 3. All validations in constructors (throw IllegalArgumentException with clear message)
# 4. Immutability by default (mut=false)
# 5. Factory methods as static methods (e.g., Money.of(...))
# 6. Methods returning new instances for VOs (e.g., Money.add() returns new Money)
# 7. Domain events stored in List inside AggregateRoot, cleared after publishing
# 8. No setters, use behavior methods (e.g., order.confirm() instead of order.setStatus())
# 9. No dependency on frameworks in domain layer (pure Java)
# 10. Package-private constructors for ARs and Entities, public factory methods
#
# Order State Transitions (validation in OrderStatus.canTransitionTo()):
#   PENDING -> CONFIRMED | CANCELLED
#   CONFIRMED -> SHIPPED | CANCELLED
#   SHIPPED -> DELIVERED
#   DELIVERED -> (final state, no transitions)
#   CANCELLED -> (final state, no transitions)
#
# Critical Business Rules:
# - Order.totalAmount MUST equal sum of all OrderItem.subtotal
# - OrderItem.subtotal MUST equal quantity * unitPrice
# - Product.stock CANNOT be negative
# - Product.price MUST be > 0
# - OrderItem.productName and unitPrice are SNAPSHOTS (immutable after creation)
# - Stock decremented ONLY on OrderConfirmed event, NOT on OrderCreated
# - If order cancelled after confirmed, stock MUST be released
# - SKU is immutable after Product creation
# - Currency must be consistent within an Order (all Money with same Currency)
#
# ============================================================================


