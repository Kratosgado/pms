
package com.kratosgado.pms.data;

import java.util.ArrayList;

import com.kratosgado.pms.interfaces.InMemoryDatabase;
import com.kratosgado.pms.models.Project;

public class ProjectInMemoryDatabase implements InMemoryDatabase<Project> {
  private ArrayList<Project> projects;

  public ProjectInMemoryDatabase() {
    projects = new ArrayList<>();
  }

  @Override
  public Project add(Project model) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'add'");
  }

  @Override
  public Project update(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public ArrayList<Project> getAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAll'");
  }

  @Override
  public Project getById(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getById'");
  }

  @Override
  public Project getWhere(String field, String value) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getWhere'");
  }

  @Override
  public void removeById(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'removeById'");
  }

  @Override
  public int count() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'count'");
  }

}
