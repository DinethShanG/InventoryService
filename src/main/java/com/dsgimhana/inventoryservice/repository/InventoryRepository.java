/*
 * Copyright (c) 2023 DSGIMHANA
 * Author: H.G.D.S GIMHANA
 */
package com.dsgimhana.inventoryservice.repository;

import com.dsgimhana.inventoryservice.entity.InventoryEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
  Optional<InventoryEntity> findById(Long id);

  @Modifying(clearAutomatically = true)
  @Query("UPDATE InventoryEntity i SET i.allocation = :allocation, i.available = :available WHERE i.id = :id")
  void allocateInventory(@Param("id") Long id, @Param("allocation") int allocation, @Param("available") int available);

  @Modifying(clearAutomatically = true)
  @Query(
      "UPDATE InventoryEntity i SET i.sold = i.sold + :count, i.available = i.available - :count WHERE i.id = :id")
  void sellInventory(@Param("id") Long id, @Param("count") int count);

  @Modifying(clearAutomatically = true)
  @Query(
      "UPDATE InventoryEntity i SET i.sold = i.sold - :count, i.available = i.available + :count WHERE i.id = :id")
  void cancelInventory(@Param("id") Long id, @Param("count") int count);

  List<InventoryEntity> findAllByAvailableGreaterThanEqual(int count);

  List<InventoryEntity> findAll();
}
