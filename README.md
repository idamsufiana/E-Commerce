
# E-Commerce

E-Commerce backend (microservices + event-driven)

## Service Interaction Flow
flowchart TD

<img width="563" height="678" alt="image" src="https://github.com/user-attachments/assets/9ad05825-e105-4fee-a6bf-7e72b2f8f966" />

Event Communication Summary Table


No  | Event
| Producer
| Consumer
------------------------------------------------------------

1   | OrderCreated
| order-service
| catalog-service, payment-service


2   | OrderCanceled
| order-service
| catalog-service


3   | OrderExpired
| order-service
| catalog-service


4   | StockReserved
| catalog-service
| order-service


5   | StockRejected
| catalog-service
| order-service


6   | PaymentPending
| payment-service
| order-service


7   | PaymentSucceeded
| payment-service
| order-service, shipping-service


8   | PaymentFailed
| payment-service
| order-service


9   | PaymentExpired
| payment-service
| order-service


10  | OrderShipped
| shipping-service
| order-service


11  | OrderDelivered
| shipping-service
| order-service


------------------------------------------------------------


