/*
 * Copyright (c) 2023 DSGIMHANA
 * Author: H.G.D.S GIMHANA
 */
package com.dsgimhana.inventoryservice.dto.response;

import lombok.Data;

@Data
public class InventoryRS {
  private Long id;
  private int allocation;
  private int sold;
  private int available;
  private int cancel;
}
