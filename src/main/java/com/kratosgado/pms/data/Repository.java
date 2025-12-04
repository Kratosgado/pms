
package com.kratosgado.pms.data;

import java.util.Arrays;

import com.kratosgado.pms.interfaces.HasId;
import com.kratosgado.pms.interfaces.InMemoryDatabase;

public abstract class Repository<T extends HasId> implements InMemoryDatabase<T> {
  protected T[] entities;
  protected int capacity;

  public Repository() {
    this.capacity = 0;
  }

  public T innerAdd(T entity) {
    if (entities.length == capacity) {
      ensureCapacity((int) (entities.length * 1.5));
    }
    entities[entities.length - 1] = entity;
    return entity;
  }

  @Override
  public T update(T model) {
    T entity = getById(model.getId());
    entity = model;
    return entity;
  }

  @Override
  public T[] getAll() {
    return Arrays.copyOf(entities, entities.length);
  }

  @Override
  public T getById(String id) {
    for (T t : entities) {
      if (t.getId().equals(id))
        return t;
    }
    return null;
  }

  @Override
  public void removeById(String id) {
    T entity = getById(id);
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
    return getById(id) != null;
  }

  @Override
  public int count() {
    return entities.length;
  }

  // @Override
  // public T[] getWhere(String field, String value) {
  // T.class.getDeclaredField(field);
  // Field field = T.getClass().getDeclaredField(field);
  // ArrayList<T> results = new ArrayList<>();
  // for (T entity : entities) {
  // if (entity.get(field).equals(value))
  // results.add(entity);
  // }
  // return results.toArray(new T[0]);
  // }

  private void ensureCapacity(int minCapacity) {
    if (minCapacity > capacity) {
      int newCapacity = Math.max(minCapacity, capacity * 2);
      T[] newEntities = (T[]) new Object[newCapacity];
      System.arraycopy(entities, 0, newEntities, 0, entities.length);
      entities = newEntities;
      capacity = newCapacity;
    }
  }

}
