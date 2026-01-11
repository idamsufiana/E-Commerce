package com.makeup.catalog.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "inventory")
@Data
public class Inventory {

    @Id
    private Long InventoryId;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer stockOnHand;

    private Integer reservedStock;
}
