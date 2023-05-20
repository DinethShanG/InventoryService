/*
 * Copyright (c) 2023 DSGIMHANA
 * Author: H.G.D.S GIMHANA
 */
package com.dsgimhana.inventoryservice.service.impl;

import com.dsgimhana.inventoryservice.entity.ProductEntity;
import com.dsgimhana.inventoryservice.exception.NotFoundException;
import com.dsgimhana.inventoryservice.model.ProductMessage;
import com.dsgimhana.inventoryservice.repository.ProductRepository;
import com.dsgimhana.inventoryservice.service.ProductService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product with id %d not found";

  private final ProductRepository productRepository;

  @Autowired private ModelMapper modelMapper;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  @Transactional
  public ProductEntity getProductById(Long id) {
    Optional<ProductEntity> product = productRepository.findById(id);
    if (product.isPresent()) {
      return product.get();
    } else {
      throw new NotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE, id));
    }
  }

  @Override
  @Transactional
  public ProductEntity createProduct(ProductMessage product) {
    return productRepository.save(modelMapper.map(product, ProductEntity.class));
  }

  @Override
  @Transactional
  public ProductEntity updateProduct(ProductMessage product) {
    Optional<ProductEntity> optionalProduct = productRepository.findById(product.getId());
    if (optionalProduct.isPresent()) {
      ProductEntity existingProductEntity = optionalProduct.get();
      existingProductEntity.setTitle(product.getTitle());
      existingProductEntity.setPrice(product.getPrice());
      return productRepository.save(existingProductEntity);
    } else {
      throw new NotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE, product.getId()));
    }
  }

  @Override
  @Transactional
  @CacheEvict(value = "products", allEntries = true)
  public void deleteProduct(ProductMessage product) {
    Optional<ProductEntity> optionalProduct = productRepository.findById(product.getId());
    if (optionalProduct.isPresent()) {
      productRepository.deleteById(product.getId());
    } else {
      throw new NotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE, product.getId()));
    }
  }
}
