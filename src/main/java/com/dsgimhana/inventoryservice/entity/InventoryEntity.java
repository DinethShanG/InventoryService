/*
 * Copyright (c) 2023 DSGIMHANA
 * Author: H.G.D.S GIMHANA
 */
package com.dsgimhana.inventoryservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "inventory")
public class InventoryEntity {
  @Id private Long id;

  @Column(name = "allocation")
  private int allocation;

  @Column(name = "sold")
  private int sold;

  @Column(name = "available")
  private int available;

  @Column(name = "cancel")
  private int cancel;

  public InventoryEntity(Long id, int allocation, int sold, int available, int cancel) {
    this.id = id;
    this.allocation = allocation;
    this.sold = sold;
    this.available = available;
    this.cancel = cancel;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    InventoryEntity that = (InventoryEntity) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
