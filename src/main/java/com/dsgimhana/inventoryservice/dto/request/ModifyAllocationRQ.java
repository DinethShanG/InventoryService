/*
 * Copyright (c) 2023 DSGIMHANA
 * Author: H.G.D.S GIMHANA
 */
package com.dsgimhana.inventoryservice.dto.request;

import lombok.Data;

@Data
public class ModifyAllocationRQ {
  private Long productId;
  private int allocation;
  private ModifyAllocationType modifyAllocationType;
}
