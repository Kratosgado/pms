
package com.kratosgado.pms.interfaces;

public interface InMemoryDatabase<T> {

  public T add(T model);

  public T update(T model);

  public T[] getAll();

  public T getById(String id);

  public void removeById(String id);

  public boolean exists(String id);

  public int count();

}
