# E-Commerce
E-Commerce backend 


           ┌─────────────┐
           │ auth-service│
           └─────┬───────┘
                 │ JWT (userId)
                 ▼
           ┌─────────────┐
           │ order-service│
           └─────┬───────┘
      OrderCreated│
        ┌─────────┼─────────┐
        ▼         ▼         │
┌────────────┐ ┌──────────────┐
│catalog-svc │ │ payment-svc  │
│(reserve stk)││(create pay)  │
└────────────┘ └──────┬───────┘
                       │ PaymentSucceeded
                       ├───────────────┐
                       ▼               ▼
              ┌─────────────┐  ┌──────────────┐
              │ order-service│  │ shipping-svc │
              │ (PAID)       │  │ (create ship)│
              └─────────────┘  └──────┬───────┘
                                       │ OrderShipped
                                       ▼
                                ┌─────────────┐
                                │ order-service│
                                │ (SHIPPED)    │
                                └─────────────┘
