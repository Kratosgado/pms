
package com.kratosgado.pms.interfaces;

import java.util.Optional;

import com.kratosgado.pms.utils.exceptions.PMSException;

public interface InMemoryDatabase<T> {

  public T add(T model);

  public T update(T model);

  public T[] getAll();

  public Optional<T> getById(String id) throws PMSException;

  public void removeById(String id);

  public boolean exists(String id);

  public int count();

}
