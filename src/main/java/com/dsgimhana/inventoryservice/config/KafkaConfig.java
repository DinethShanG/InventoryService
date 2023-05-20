/*
 * Copyright (c) 2023 DSGIMHANA
 * Author: H.G.D.S GIMHANA
 */
package com.dsgimhana.inventoryservice.config;

import com.dsgimhana.inventoryservice.model.ProductMessage;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@EnableKafka
@Configuration
@EnableConfigurationProperties({KafkaProperties.class})
public class KafkaConfig {

  private final KafkaProperties kafkaProperties;

  public KafkaConfig(KafkaProperties kafkaProperties) {
    this.kafkaProperties = kafkaProperties;
  }

  @Bean
  public RecordMessageConverter converter() {
    return new StringJsonMessageConverter();
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, ProductMessage>
      kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, ProductMessage> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    factory.setRecordMessageConverter(new StringJsonMessageConverter());
    return factory;
  }

  @Bean
  public ConsumerFactory<String, ProductMessage> consumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapAddress());
    props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
    return new DefaultKafkaConsumerFactory<>(props);
  }
}
