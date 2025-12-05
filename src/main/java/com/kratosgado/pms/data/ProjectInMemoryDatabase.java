
package com.kratosgado.pms.data;

import java.util.Arrays;
import java.util.function.Predicate;

import com.kratosgado.pms.interfaces.Filterable;
import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.enums.ProjectType;
import com.kratosgado.pms.utils.factories.ModelFactory;

public class ProjectInMemoryDatabase extends Repository<Project> implements Filterable<Project> {

  public ProjectInMemoryDatabase() {
  }

  public ProjectInMemoryDatabase(Project[] entities) {
    super(entities);
  }

  public Project add(String name, String description, int teamSize, double budget, String type) {
    String id = CustomUtils.getNextId("PJ", count());
    return safeAdd(ModelFactory.createProject(id, name, description, teamSize, budget, ProjectType.valueOf(type)));
  }

  @Override
  public Project[] filter(Predicate<Project> predicate) {
    return Arrays.stream(entities).filter(predicate).toArray(Project[]::new);
  }

  @Override
  public Project add(Project model) {
    return safeAdd(model);
  }

}
