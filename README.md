# E-Commerce
E-Commerce backend 

+----------------+
|  auth-service  |
+----------------+
        |
        |  JWT (userId)
        v
+----------------+
| order-service  |
+----------------+
        |
        |  OrderCreated
        |
        +--------------------+
        |                    |
        v                    v
+----------------+    +------------------+
| catalog-service|    | payment-service  |
| reserve stock  |    | create payment   |
+----------------+    +------------------+
                            |
                            | PaymentSucceeded
                            |
                +---------------------------+
                |                           |
                v                           v
        +----------------+        +------------------+
        | order-service  |        | shipping-service |
        | status = PAID  |        | create shipment  |
        +----------------+        +------------------+
                                            |
                                            | OrderShipped
                                            v
                                    +----------------+
                                    | order-service  |
                                    | status=SHIPPED |
                                    +----------------+

