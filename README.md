# E-Commerce
E-Commerce backend 

auth-service
  |
  | JWT (userId)
  v
order-service
  |
  | OrderCreated
  +--> catalog-service   (reserve stock)
  |
  +--> payment-service   (create payment)
            |
            | PaymentSucceeded
            v
      shipping-service   (create shipment)
            |
            | OrderShipped
            v
      order-service      (mark SHIPPED)


