
package com.kratosgado.pms.data;

import java.util.Arrays;
import java.util.Optional;

import com.kratosgado.pms.interfaces.HasId;
import com.kratosgado.pms.interfaces.InMemoryDatabase;
import com.kratosgado.pms.utils.CustomUtils;

public abstract class Repository<T extends HasId> implements InMemoryDatabase<T> {
  protected T[] entities;
  protected int capacity;

  public Repository() {
    this.capacity = CustomUtils.DEFAULT_MEMORY_CAPACITY;
  }

  protected T safeAdd(T entity) {
    if (entities.length == capacity) {
      ensureCapacity();
    }
    entities[entities.length - 1] = entity;
    return entity;
  }

  @Override
  public T update(T model) {
    T entity = getById(model.getId()).orElseThrow();
    entity = model;
    return entity;
  }

  @Override
  public T[] getAll() {
    return Arrays.copyOf(entities, entities.length);
  }

  @Override
  public Optional<T> getById(String id) {
    return Arrays.stream(entities).filter(t -> t.getId().equals(id)).findFirst();
  }

  @Override
  public void removeById(String id) {
    T entity = getById(id).orElse(null);
    if (entity != null) {
      int index = Arrays.asList(entities).indexOf(entity);
      if (index >= 0) {
        entities[index] = null;
        entities = Arrays.copyOf(entities, entities.length - 1);
      }
    }

  }

  @Override
  public boolean exists(String id) {
    return getById(id).isPresent();
  }

  @Override
  public int count() {
    return entities.length;
  }

  private void ensureCapacity() {
    int newCapacity = capacity * 2;
    T[] newEntities = (T[]) new Object[newCapacity];
    System.arraycopy(entities, 0, newEntities, 0, entities.length);
    entities = newEntities;
    capacity = newCapacity;
  }

}
