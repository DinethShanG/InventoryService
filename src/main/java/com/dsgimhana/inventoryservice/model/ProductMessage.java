/*
 * Copyright (c) 2023 DSGIMHANA
 * Author: H.G.D.S GIMHANA
 */
package com.dsgimhana.inventoryservice.model;

import lombok.Data;

@Data
public class ProductMessage {
  private Long id;

  private String title;

  private String price;

  private ProductAction action;
}
