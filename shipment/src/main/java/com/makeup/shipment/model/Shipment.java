package com.makeup.shipment.model;

@Entity
@Table(name = "shipments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private String orderNo;

    private String courier;
    private String service;

    private String trackingNo;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
}
