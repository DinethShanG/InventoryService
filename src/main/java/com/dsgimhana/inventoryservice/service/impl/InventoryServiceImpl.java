/*
 * Copyright (c) 2023 DSGIMHANA
 * Author: H.G.D.S GIMHANA
 */
package com.dsgimhana.inventoryservice.service.impl;

import com.dsgimhana.inventoryservice.dto.request.CancelInventoryRQ;
import com.dsgimhana.inventoryservice.dto.request.ModifyAllocationRQ;
import com.dsgimhana.inventoryservice.dto.request.SellInventoryRQ;
import com.dsgimhana.inventoryservice.entity.InventoryEntity;
import com.dsgimhana.inventoryservice.entity.ProductEntity;
import com.dsgimhana.inventoryservice.exception.NotFoundException;
import com.dsgimhana.inventoryservice.model.ProductAction;
import com.dsgimhana.inventoryservice.model.ProductMessage;
import com.dsgimhana.inventoryservice.repository.InventoryRepository;
import com.dsgimhana.inventoryservice.service.InventoryService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {
  private static final String INVENTORY_NOT_FOUND_MESSAGE = "Inventory with id %d not found";
  private static final String PRODUCT_ACTION_NOT_FOUND_MESSAGE =
      "Product Action %s is not founded for product id %d";

  private final InventoryRepository inventoryRepository;

  private final ProductServiceImpl productService;

  public InventoryServiceImpl(
      InventoryRepository inventoryRepository, ProductServiceImpl productService) {
    this.inventoryRepository = inventoryRepository;
    this.productService = productService;
  }

  @Transactional
  @Override
  public void syncProductWithInventory(ProductMessage productMessage) {

    ProductAction action = productMessage.getAction();

    if (action == ProductAction.CREATED) {
      ProductEntity productEntity = productService.createProduct(productMessage);
      InventoryEntity initialInventoryEntity = getInitialInventoryEntity(productEntity);
      inventoryRepository.save(initialInventoryEntity);
    } else if (action == ProductAction.UPDATED) {
      productService.updateProduct(productMessage);
    } else if (action == ProductAction.DELETED) {
      productService.deleteProduct(productMessage);
    } else {
      throw new NotFoundException(
          String.format(
              PRODUCT_ACTION_NOT_FOUND_MESSAGE,
              productMessage.getAction(),
              productMessage.getId()));
    }
  }

  private InventoryEntity getInitialInventoryEntity(ProductEntity productEntity) {
    return new InventoryEntity(productEntity.getId(), 0, 0, 0, 0);
  }

  @Override
  public InventoryEntity getInventoryById(Long id) {
    Optional<InventoryEntity> inventory = inventoryRepository.findById(id);
    if (inventory.isPresent()) {
      return inventory.get();
    } else {
      throw new NotFoundException(String.format(INVENTORY_NOT_FOUND_MESSAGE, id));
    }
  }

  @Override
  public InventoryEntity updateInventoryAllocationById(ModifyAllocationRQ modifyAllocationRQ) {
    Long productId = modifyAllocationRQ.getProductId();
    int count = modifyAllocationRQ.getAllocation();

    Optional<InventoryEntity> inventory = inventoryRepository.findById(productId);
    if (inventory.isPresent()) {
      inventoryRepository.allocateInventory(productId, count);
      return inventory.get();
    } else {
      throw new NotFoundException(String.format(INVENTORY_NOT_FOUND_MESSAGE, productId));
    }
  }

  @Override
  public InventoryEntity sellInventoryById(SellInventoryRQ sellInventoryRQ) {
    Long id = sellInventoryRQ.getProductId();
    int count = sellInventoryRQ.getCount();

    Optional<InventoryEntity> inventory = inventoryRepository.findById(id);
    if (inventory.isPresent() && inventory.get().getAvailable() >= count) {
      inventoryRepository.sellInventory(id, count);
      return inventory.get();
    } else {
      throw new NotFoundException(String.format(INVENTORY_NOT_FOUND_MESSAGE, id));
    }
  }

  @Override
  public InventoryEntity cancelInventoryById(CancelInventoryRQ cancelInventoryRQ) {
    Long productId = cancelInventoryRQ.getProductId();
    int count = cancelInventoryRQ.getCount();

    Optional<InventoryEntity> inventory = inventoryRepository.findById(productId);
    if (inventory.isPresent() && inventory.get().getSold() >= count) {
      inventoryRepository.cancelInventory(productId, count);
      return inventory.get();
    } else {
      throw new NotFoundException(String.format(INVENTORY_NOT_FOUND_MESSAGE, productId));
    }
  }

  @Override
  public List<InventoryEntity> getAllAvailableInventories() {
    return inventoryRepository.findAllByAvailableGreaterThanEqual(1);
  }

  @Override
  public List<InventoryEntity> getAllInventories() {
    return inventoryRepository.findAll();
  }
}
