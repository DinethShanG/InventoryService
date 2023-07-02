/*
 * Copyright (c) 2023 DSGIMHANA
 * Author: H.G.D.S GIMHANA
 */
package com.dsgimhana.inventoryservice.consumer;

import com.dsgimhana.inventoryservice.model.ProductMessage;
import com.dsgimhana.inventoryservice.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ProductConsumer {
  private static final Logger logger = LoggerFactory.getLogger(ProductConsumer.class);
  @Autowired private InventoryService inventoryService;

  @KafkaListener(
      topics = "${kafka.productConsumerTopic}",
      groupId = "${kafka.groupId}",
      containerFactory = "kafkaListenerContainerFactory")
  public void consumeProduct(@Payload ProductMessage productMessage) {
    try {
      logger.info("Started to consume product from product service Kafka {}", productMessage);
      inventoryService.syncProductWithInventory(productMessage);
      logger.info("Completed consuming product from product service: {}", productMessage);
    } catch (Exception e) {
      logger.error(
          "Error while consuming and processing product from product service: " + productMessage,
          e);
    }
  }
}
