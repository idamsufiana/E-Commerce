package com.makeup.catalog.service;

import com.makeup.catalog.dto.Item;
import com.makeup.catalog.dto.OrderCreatedEvent;
import com.makeup.catalog.model.Inventory;
import com.makeup.catalog.model.ProductStatus;
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

            Inventory inventory = getInventory(item.getProductId());

            validateReserve(inventory, item.getQty());

            int updated = inventoryRepository.reserveStock(
                    item.getProductId(),
                    item.getQty()
            );

            if (updated == 0) {
                throw new IllegalStateException(
                        "Stock not enough for product " + item.getProductId()
                );
            }

            updateStatus(inventory);
        }
    }

    @Transactional
    public void consume(List<Item> items) {

        for (Item item : items) {

            Inventory inventory = getInventory(item.getProductId());

            validateConsume(inventory, item.getQty());

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

            updateStatus(inventory);
        }
    }

    @Transactional
    public void release(List<Item> items) {

        for (Item item : items) {

            Inventory inventory = getInventory(item.getProductId());

            inventoryRepository.releaseStock(
                    item.getProductId(),
                    item.getQty()
            );

            updateStatus(inventory);
        }
    }

    private void validateReserve(Inventory inventory, int qty) {

        if (inventory.getProduct().getStatus() == ProductStatus.INACTIVE) {
            throw new IllegalStateException("Product is inactive");
        }

        if (inventory.getStockOnHand() < qty) {
            throw new IllegalStateException("Insufficient stock");
        }

        if (inventory.getStockOnHand() == 0) {
            throw new IllegalStateException("Product out of stock");
        }
    }

    private void validateConsume(Inventory inventory, int qty) {

        if (inventory.getProduct().getStatus() == ProductStatus.INACTIVE) {
            throw new IllegalStateException("Product is inactive");
        }

        if (inventory.getReservedStock() < qty) {
            throw new IllegalStateException("Reserved stock not enough");
        }
    }

    private void updateStatus(Inventory inventory) {

        if (inventory.getStockOnHand() == 0 &&
                inventory.getReservedStock() == 0) {

            inventory.getProduct().setStatus(ProductStatus.OUT_OF_STOCK);
        }

        if (inventory.getStockOnHand() > 0 &&
                inventory.getProduct().getStatus() == ProductStatus.OUT_OF_STOCK) {

            inventory.getProduct().setStatus(ProductStatus.ACTIVE);
        }
    }

    private Inventory getInventory(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() ->
                        new IllegalStateException("Product not found: " + productId));
    }
}