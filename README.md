# E-Commerce
E-Commerce backend 


┌──────────────┐
│ auth-service │
└───────┬──────┘
        │ JWT (userId)
        ▼
┌──────────────┐
│ order-service│
└───────┬──────┘
        │ OrderCreated
        ├──────────────► catalog-service
        │                 (reserve stock)
        │
        └──────────────► payment-service
                          (create payment)
                               │
                               ▼
                       PaymentSucceeded
                               │
        ┌──────────────────────┴──────────────────────┐
        ▼                                             ▼
order-service                                  shipping-service
(mark PAID)                                   (create shipment)
        │                                             │
        └────────────── OrderShipped ◄────────────────┘
                               │
                               ▼
                         order-service
                         (mark SHIPPED)

