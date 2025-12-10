
package com.kratosgado.pms.data;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import com.kratosgado.pms.interfaces.Filterable;
import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.enums.ProjectType;
import com.kratosgado.pms.utils.factories.ModelFactory;

public class ProjectInMemoryDatabase extends Repository<Project> implements Filterable<Project> {

  public ProjectInMemoryDatabase() {
  }

  public ProjectInMemoryDatabase(HashMap<String, Project> entities) {
    super(entities);
  }

  public Project add(String name, String description, int teamSize, double budget, ProjectType type) {
    String id = CustomUtils.getNextId("PJ", count());
    return safeAdd(ModelFactory.createProject(id, name, description, teamSize, budget, type));
  }

  @Override
  public List<Project> filter(Predicate<Project> predicate) {
    return entities.values().stream().filter(predicate).toList();

  }

  @Override
  public Project add(Project model) {
    return safeAdd(model);
  }

}
