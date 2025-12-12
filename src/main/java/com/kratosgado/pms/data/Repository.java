
package com.kratosgado.pms.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.kratosgado.pms.interfaces.HasId;
import com.kratosgado.pms.interfaces.InMemoryDatabase;
import com.kratosgado.pms.utils.exceptions.ConflictException;

public abstract class Repository<T extends HasId> implements InMemoryDatabase<T> {
  protected HashMap<String, T> entities;

  public Repository() {
    this.entities = new HashMap<>();
  }

  public Repository(HashMap<String, T> entities) {
    this.entities = entities;
  }

  protected T safeAdd(T entity) {
    if (exists(entity.getId())) {
      throw new ConflictException("Entity with id " + entity.getId() + " already exists");
    }

    this.entities.put(entity.getId(), entity);
    return entity;
  }

  @Override
  public T update(T model) {
    T entity = getById(model.getId()).orElseThrow();
    entity = model;
    return entity;
  }

  @Override
  public List<T> getAll() {
    return Collections.unmodifiableList(entities.values().stream().toList());
  }

  @Override
  public Optional<T> getById(String id) {
    T entity = entities.get(id);
    if (entity == null) {
      return Optional.empty();
    }
    return Optional.of(entity);
  }

  @Override
  public boolean removeById(String id) {
    T entity = entities.remove(id);
    return entity != null;
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
