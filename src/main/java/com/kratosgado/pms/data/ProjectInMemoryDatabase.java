
package com.kratosgado.pms.data;

import java.util.ArrayList;

import com.kratosgado.pms.interfaces.InMemoryDatabase;
import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.Seed;

public class ProjectInMemoryDatabase implements InMemoryDatabase<Project> {
  private ArrayList<Project> projects;

  public ProjectInMemoryDatabase() {
    projects = Seed.seedProjects();
  }

  @Override
  public Project add(Project model) {
    String id = CustomUtils.getNextId("PJ", count());
    // TODO: Project project = new Project(id, model.getName(),
    // model.getDescription(),
    // model.getTeamSize(), model.getBudget());
    projects.add(model);
    return model;
  }

  @Override
  public Project update(Project model) {
    // TODO: Project project = new Project(id, model.getName(),
    // model.getDescription(),
    // model.getTeamSize(), model.getBudget());
    Project project = getById(model.getId());
    project.setName(model.getName());
    project.setDescription(model.getDescription());
    project.setTeamSize(model.getTeamSize());
    project.setBudget(model.getBudget());
    return project;
  }

  @Override
  public ArrayList<Project> getAll() {
    return new ArrayList<>(projects);
  }

  @Override
  public Project getById(String id) {
    for (final Project project : projects) {
      if (project.getId().equals(id)) {
        return project;
      }
    }
    throw new IllegalArgumentException("Project not found");
  }

  @Override
  public Project getWhere(String field, String value) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getWhere'");
  }

  @Override
  public void removeById(String id) {
    Project project = getById(id);
    projects.remove(project);
  }

  @Override
  public int count() {
    return projects.size();
  }

  @Override
  public boolean exists(String id) {
    return projects.stream().anyMatch(project -> project.getId().equals(id));
  }

}
