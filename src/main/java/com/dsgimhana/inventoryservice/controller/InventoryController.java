/*
 * Copyright (c) 2023 DSGIMHANA
 * Author: H.G.D.S GIMHANA
 */
package com.dsgimhana.inventoryservice.controller;

import com.dsgimhana.inventoryservice.dto.request.CancelInventoryRQ;
import com.dsgimhana.inventoryservice.dto.request.ModifyAllocationRQ;
import com.dsgimhana.inventoryservice.dto.request.SellInventoryRQ;
import com.dsgimhana.inventoryservice.dto.response.InventoryRS;
import com.dsgimhana.inventoryservice.entity.InventoryEntity;
import com.dsgimhana.inventoryservice.service.InventoryService;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
  private final InventoryService inventoryService;

  private final ModelMapper modelMapper;

  public InventoryController(InventoryService inventoryService, ModelMapper modelMapper) {
    this.inventoryService = inventoryService;
    this.modelMapper = modelMapper;
  }

  @GetMapping
  public List<InventoryRS> getAllInventories() {
    List<InventoryEntity> inventories = inventoryService.getAllInventories();
    return inventories.stream().map(this::mapToDto).toList();
  }

  @GetMapping("/available")
  public List<InventoryRS> getAllAvailableInventories() {
    List<InventoryEntity> inventories = inventoryService.getAllAvailableInventories();
    return inventories.stream().map(this::mapToDto).toList();
  }

  @GetMapping("/{id}")
  public InventoryRS getInventoryById(@PathVariable Long id) {
    InventoryEntity inventory = inventoryService.getInventoryById(id);
    return mapToDto(inventory);
  }

  @PostMapping("/allocation")
  public InventoryRS updateInventoryAllocationById(
      @RequestBody ModifyAllocationRQ modifyAllocationRQ) {
    InventoryEntity inventory = inventoryService.updateInventoryAllocationById(modifyAllocationRQ);
    return mapToDto(inventory);
  }

  @PostMapping("/sell")
  public InventoryRS sellInventoryById(@RequestBody SellInventoryRQ sellInventoryRQ) {
    InventoryEntity inventory = inventoryService.sellInventoryById(sellInventoryRQ);
    return mapToDto(inventory);
  }

  @PostMapping("/cancel")
  public InventoryRS cancelInventoryById(@RequestBody CancelInventoryRQ cancelInventoryRQ) {
    InventoryEntity inventory = inventoryService.cancelInventoryById(cancelInventoryRQ);
    return mapToDto(inventory);
  }

  private InventoryRS mapToDto(InventoryEntity inventory) {
    return modelMapper.map(inventory, InventoryRS.class);
  }
}
