
package com.kratosgado.pms.data;

import java.util.Arrays;
import java.util.Optional;

import com.kratosgado.pms.interfaces.HasId;
import com.kratosgado.pms.interfaces.InMemoryDatabase;
import com.kratosgado.pms.utils.CustomUtils;

public abstract class Repository<T extends HasId> implements InMemoryDatabase<T> {
  protected T[] entities;
  protected int size;

  public Repository() {
    this.size = 0;
    this.entities = (T[]) new HasId[CustomUtils.DEFAULT_MEMORY_CAPACITY];
  }

  public Repository(T[] entities) {
    this.size = entities.length;
    this.entities = entities;
  }

  protected T safeAdd(T entity) {
    if (entities.length == size) {
      ensureCapacity();
    }
    entities[size++] = entity;
    return entity;
  }

  private int getIndexById(String id) {
    for (int i = 0; i < size; i++) {
      if (entities[i].getId().equals(id)) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public T update(T model) {
    T entity = getById(model.getId()).orElseThrow();
    entity = model;
    return entity;
  }

  @Override
  public T[] getAll() {
    return Arrays.copyOf(entities, size);
  }

  @Override
  public Optional<T> getById(String id) {
    int index = getIndexById(id);
    if (index >= 0) {
      return Optional.of(entities[index]);
    }
    return Optional.empty();
  }

  @Override
  public boolean removeById(String id) {
    int index = getIndexById(id);
    if (index < 0)
      return false;

    T[] newEntities = Arrays.copyOf(entities, --size);
    int currentIndex = 0;
    for (int i = index + 1; i <= size; i++) {
      newEntities[currentIndex++] = entities[i];
    }
    entities = (T[]) newEntities;
    return true;
  }

  @Override
  public boolean exists(String id) {
    return getIndexById(id) >= 0;
  }

  @Override
  public int count() {
    return size;
  }

  private void ensureCapacity() {
    int newCapacity = size * 2;
    T[] newEntities = (T[]) new HasId[newCapacity];
    System.arraycopy(entities, 0, newEntities, 0, entities.length);
    entities = newEntities;
  }

}
