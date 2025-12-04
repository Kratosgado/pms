
package com.kratosgado.pms.interfaces;

public interface Searchable<T> {

  public T getWhere(String field, String value);
}
