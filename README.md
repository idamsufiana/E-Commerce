
# E-Commerce

E-Commerce backend (microservices + event-driven)

## Service Interaction Flow
flowchart TD


user
 ↓
customer
 ↓
cart
 ↓
cart_item
 ↓ checkout
inventory.reserve()
 ↓
order
 ↓
order_item
 ↓
payment
 ↓
order = PAID
 ↓
shipment
 ↓
order = SHIPPED
 ↓
order = DELIVERED

------------------------------------------------------------


