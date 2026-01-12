package com.makeup.catalog.service;

import com.makeup.catalog.dto.Item;
import com.makeup.catalog.dto.OrderCreatedEvent;
import com.makeup.catalog.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public void reserve(List<Item> items) {

        for (Item item : items) {
            int updated = inventoryRepository.reserveStock(
                    item.getProductId(),
                    item.getQty()
            );

            if (updated == 0) {
                throw new IllegalStateException(
                        "Stock not enough for product " + item.getProductId()
                );
            }
        }
    }

    public void consume(List<Item> items) {
        for (Item item : items) {
            int updated = inventoryRepository.consumeReservedStock(
                    item.getProductId(),
                    item.getQty()
            );

            if (updated == 0) {
                throw new IllegalStateException(
                        "Consume reserved stock failed for product "
                                + item.getProductId()
                );
            }
        }
    }

    @Transactional
    public void release(List<Item> items) {
        for (Item item : items) {
            inventoryRepository.releaseStock(
                    item.getProductId(),
                    item.getQty()
            );
        }
    }
}