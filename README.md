
# E-Commerce

E-Commerce backend (microservices + event-driven)

## Service Interaction Flow
flowchart TD


========================================
EVENT FLOW (FROM USER TO DELIVERED)
========================================

_user
 |
 | login (JWT)
 v
customer
 |
 v
cart
 |
 v
cart_item
 |
 | checkout
 v
----------------------------------------
EVENT: CheckoutRequested
----------------------------------------
InventoryService
 |
 | reserve stock
 |
 |-- if SUCCESS
 |      publish EVENT: StockReserved
 |
 |-- if FAILED
 |      publish EVENT: StockRejected
 v
----------------------------------------
OrderService
 |
 |-- consume StockReserved
 |-- create order (status=PENDING)
 |-- create order_item (snapshot)
 |-- publish EVENT: OrderCreated
 |
 |-- consume StockRejected
 |-- reject checkout
 v
----------------------------------------
PaymentService
 |
 |-- consume OrderCreated
 |-- create payment
 |
 |-- if SUCCESS
 |      publish EVENT: PaymentSucceeded
 |
 |-- if FAILED
 |      publish EVENT: PaymentFailed
 |
 |-- if EXPIRED
 |      publish EVENT: PaymentExpired
 v
----------------------------------------
OrderService
 |
 |-- consume PaymentSucceeded
 |-- update order status = PAID
 |-- publish EVENT: OrderPaid
 |
 |-- consume PaymentFailed / PaymentExpired
 |-- update order status = CANCELLED
 |-- publish EVENT: OrderCancelled
 v
----------------------------------------
InventoryService
 |
 |-- consume OrderCancelled
 |-- release stock
 v
----------------------------------------
ShippingService
 |
 |-- consume OrderPaid
 |-- create shipment
 |-- publish EVENT: ShipmentCreated
 |
 |-- when shipped
 |      publish EVENT: ShipmentShipped
 |
 |-- when delivered
 |      publish EVENT: ShipmentDelivered
 v
----------------------------------------
OrderService
 |
 |-- consume ShipmentShipped
 |-- update order status = SHIPPED
 |
 |-- consume ShipmentDelivered
 |-- update order status = DELIVERED



