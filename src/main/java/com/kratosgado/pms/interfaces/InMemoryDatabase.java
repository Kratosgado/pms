
package com.kratosgado.pms.interfaces;

import java.util.ArrayList;

public interface InMemoryDatabase<Model> {

  public Model add(Model model);

  public Model update(String id);

  public ArrayList<Model> getAll();

  public Model getById(String id);

  public Model getWhere(String field, String value);

  public void removeById(String id);

  public int count();

}
