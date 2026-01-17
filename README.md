# 🛒 E-Commerce Backend
**Microservices + Event-Driven Architecture **

This project demonstrates an e-commerce backend architecture using
**microservices**, **event-driven communication**, and **eventual consistency**
to safely handle checkout, payment, inventory, and shipment.

---

## 🧩 Services Overview

- **Auth Service** – Authentication & JWT
- **Customer Service** – Customer profile
- **Cart Service** – Temporary shopping cart
- **Inventory Service** – Stock authority (reserve / release)
- **Order Service** – Order lifecycle & status
- **Payment Service** – Payment processing
- **Shipping Service** – Shipment lifecycle

---

## 🔁 Service Interaction Flow

```mermaid
flowchart TD
    User[_user] -->|JWT| Customer[customer]
    Customer --> Cart[cart]
    Cart --> CartItem[cart_item]
    CartItem -->|checkout| CheckoutRequested[CheckoutRequested Event]

    CheckoutRequested --> Inventory[Inventory Service]
    Inventory -->|StockReserved| Order[Order Service]
    Inventory -->|StockRejected| CheckoutFailed[Checkout Rejected]

    Order -->|OrderCreated| Payment[Payment Service]
    Payment -->|PaymentSucceeded| Order
    Payment -->|PaymentFailed / PaymentExpired| Order

    Order -->|OrderPaid| Shipping[Shipping Service]
    Shipping -->|ShipmentShipped| Order
    Shipping -->|ShipmentDelivered| Order
