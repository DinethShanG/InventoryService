/*
 * Copyright (c) 2023 DSGIMHANA
 * Author: H.G.D.S GIMHANA
 */
package com.dsgimhana.inventoryservice.dto.request;

import lombok.Data;

@Data
public class SellInventoryRQ {
  private Long productId;
  private int count;
}
