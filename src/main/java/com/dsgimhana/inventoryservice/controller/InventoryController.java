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
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

  private final Logger logger = LoggerFactory.getLogger(InventoryController.class);
  private final InventoryService inventoryService;
  private final ModelMapper modelMapper;

  public InventoryController(InventoryService inventoryService, ModelMapper modelMapper) {
    this.inventoryService = inventoryService;
    this.modelMapper = modelMapper;
  }

  @GetMapping
  public ResponseEntity<List<InventoryRS>> getAllInventories() {
    try {
      List<InventoryEntity> inventories = inventoryService.getAllInventories();
      List<InventoryRS> inventoryResponseList = inventories.stream().map(this::mapToDto).toList();
      return ResponseEntity.ok(inventoryResponseList);
    } catch (Exception e) {
      logger.error("Failed to retrieve all inventories: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/available")
  public ResponseEntity<List<InventoryRS>> getAllAvailableInventories() {
    try {
      List<InventoryEntity> inventories = inventoryService.getAllAvailableInventories();
      List<InventoryRS> inventoryResponseList = inventories.stream().map(this::mapToDto).toList();
      return ResponseEntity.ok(inventoryResponseList);
    } catch (Exception e) {
      logger.error("Failed to retrieve all available inventories: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<InventoryRS> getInventoryById(@PathVariable Long id) {
    try {
      InventoryEntity inventory = inventoryService.getInventoryById(id);
      if (inventory != null) {
        InventoryRS inventoryResponse = mapToDto(inventory);
        return ResponseEntity.ok(inventoryResponse);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception e) {
      logger.error("Failed to retrieve inventory with ID {}: {}", id, e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/allocation")
  public ResponseEntity<InventoryRS> updateInventoryAllocationById(
          @RequestBody ModifyAllocationRQ modifyAllocationRQ) {
    try {
      InventoryEntity inventory = inventoryService.updateInventoryAllocationById(modifyAllocationRQ);
      InventoryRS inventoryResponse = mapToDto(inventory);
      return ResponseEntity.ok(inventoryResponse);
    } catch (Exception e) {
      logger.error("Failed to update inventory allocation: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/sell")
  public ResponseEntity<InventoryRS> sellInventoryById(@RequestBody SellInventoryRQ sellInventoryRQ) {
    try {
      InventoryEntity inventory = inventoryService.sellInventoryById(sellInventoryRQ);
      InventoryRS inventoryResponse = mapToDto(inventory);
      return ResponseEntity.ok(inventoryResponse);
    } catch (Exception e) {
      logger.error("Failed to sell inventory: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/cancel")
  public ResponseEntity<InventoryRS> cancelInventoryById(@RequestBody CancelInventoryRQ cancelInventoryRQ) {
    try {
      InventoryEntity inventory = inventoryService.cancelInventoryById(cancelInventoryRQ);
      InventoryRS inventoryResponse = mapToDto(inventory);
      return ResponseEntity.ok(inventoryResponse);
    } catch (Exception e) {
      logger.error("Failed to cancel inventory: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  private InventoryRS mapToDto(InventoryEntity inventory) {
    return modelMapper.map(inventory, InventoryRS.class);
  }
}
