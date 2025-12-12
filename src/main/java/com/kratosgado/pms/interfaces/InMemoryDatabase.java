
package com.kratosgado.pms.interfaces;

import java.util.List;
import java.util.Optional;

import com.kratosgado.pms.utils.exceptions.RuntimeException;

public interface InMemoryDatabase<T> {

  public T add(T model);

  public T update(T model);

  public List<T> getAll();

  public Optional<T> getById(String id) throws RuntimeException;

  public boolean removeById(String id);

  public boolean exists(String id);

  public int count();

}
