
# E-Commerce

E-Commerce backend (microservices + event-driven)

## Service Interaction Flow
flowchart TD


    Auth[auth-service] -->|JWT (userId)| Order[order-service]

    Order -->|OrderCreated| Catalog[catalog-service]
    Catalog -->|Reserve Stock| Catalog

    Order -->|OrderCreated| Payment[payment-service]
    Payment -->|Create Payment| Payment

    Payment -->|PaymentSucceeded| Order
    Payment -->|PaymentSucceeded| Shipping[shipping-service]

    Shipping -->|Create Shipment| Shipping
    Shipping -->|OrderShipped| Order

    Order -->|Status: SHIPPED| Order

