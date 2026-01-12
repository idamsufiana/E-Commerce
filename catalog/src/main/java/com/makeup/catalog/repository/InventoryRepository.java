package com.makeup.catalog.repository;

import com.makeup.catalog.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Modifying
    @Query("""
        UPDATE Inventory i
           SET i.stockOnHand = i.stockOnHand - :qty,
               i.reservedStock = i.reservedStock + :qty,
               i.updatedAt = CURRENT_TIMESTAMP
         WHERE i.productId = :productId
           AND i.stockOnHand >= :qty
    """)
    int reserveStock(@Param("productId") Long productId,
                     @Param("qty") int qty);

    @Modifying
    @Query("""
        UPDATE Inventory i
           SET i.stockOnHand = i.stockOnHand + :qty,
               i.reservedStock = i.reservedStock - :qty,
               i.updatedAt = CURRENT_TIMESTAMP
         WHERE i.productId = :productId
    """)
    int releaseStock(@Param("productId") Long productId,
                     @Param("qty") int qty);

    @Modifying
    @Query("""
    UPDATE Inventory i
       SET i.reservedStock = i.reservedStock - :qty,
           i.updatedAt = CURRENT_TIMESTAMP
     WHERE i.productId = :productId
       AND i.reservedStock >= :qty
""")
    int consumeReservedStock(@Param("productId") Long productId,
                             @Param("qty") int qty);
}