/*
 * Copyright (c) 2023 DSGIMHANA
 * Author: H.G.D.S GIMHANA
 */
package com.dsgimhana.inventoryservice.service;

import com.dsgimhana.inventoryservice.entity.ProductEntity;
import com.dsgimhana.inventoryservice.model.ProductMessage;

public interface ProductService {
  ProductEntity getProductById(Long id);

  ProductEntity createProduct(ProductMessage product);

  ProductEntity updateProduct(ProductMessage product);

  void deleteProduct(ProductMessage product);
}
