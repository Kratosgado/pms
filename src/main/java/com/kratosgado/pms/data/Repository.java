
package com.kratosgado.pms.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.kratosgado.pms.interfaces.HasId;
import com.kratosgado.pms.interfaces.InMemoryDatabase;
import com.kratosgado.pms.utils.exceptions.ConflictException;
import com.kratosgado.pms.utils.exceptions.EntityNotFoundException;

public abstract class Repository<T extends HasId> implements InMemoryDatabase<T> {
  protected HashMap<String, T> entities;

  public Repository() {
    this.entities = new HashMap<>();
  }

  public Repository(HashMap<String, T> entities) {
    this.entities = entities;
  }

  protected T safeAdd(T entity) throws ConflictException {
    if (exists(entity.getId())) {
      throw new ConflictException();
    }

    this.entities.put(entity.getId(), entity);
    return entity;
  }

  @Override
  public T update(T model) throws EntityNotFoundException {
    T entity = getById(model.getId());
    entity = model;
    return entity;
  }

  @Override
  public List<T> getAll() {
    return Collections.unmodifiableList(entities.values().stream().toList());
  }

  @Override
  public T getById(String id) throws EntityNotFoundException {
    T entity = entities.get(id);
    if (entity == null) {
      throw new EntityNotFoundException();
    }
    return entity;
  }

  @Override
  public void removeById(String id) throws EntityNotFoundException {
    T entity = entities.remove(id);
    if (entity == null) {
      throw new EntityNotFoundException();
    }
  }

  @Override
  public boolean exists(String id) {
    return entities.containsKey(id);
  }

  @Override
  public int count() {
    return entities.size();
  }

}
