/*
 * Copyright (c) 2023 DSGIMHANA
 * Author: H.G.D.S GIMHANA
 */
package com.dsgimhana.inventoryservice.service;

import com.dsgimhana.inventoryservice.dto.request.CancelInventoryRQ;
import com.dsgimhana.inventoryservice.dto.request.ModifyAllocationRQ;
import com.dsgimhana.inventoryservice.dto.request.SellInventoryRQ;
import com.dsgimhana.inventoryservice.entity.InventoryEntity;
import com.dsgimhana.inventoryservice.model.ProductMessage;
import java.util.List;

public interface InventoryService {

  void syncProductWithInventory(ProductMessage productMessage);

  InventoryEntity getInventoryById(Long id);

  InventoryEntity updateInventoryAllocationById(ModifyAllocationRQ modifyAllocationRQ);

  InventoryEntity sellInventoryById(SellInventoryRQ sellInventoryRQ);

  InventoryEntity cancelInventoryById(CancelInventoryRQ cancelInventoryRQ);

  List<InventoryEntity> getAllAvailableInventories();

  List<InventoryEntity> getAllInventories();
}
