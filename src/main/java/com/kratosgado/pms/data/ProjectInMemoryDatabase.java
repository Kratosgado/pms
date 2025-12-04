
package com.kratosgado.pms.data;

import java.util.Arrays;
import java.util.function.Predicate;

import com.kratosgado.pms.interfaces.Filterable;
import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.Seed;

public class ProjectInMemoryDatabase extends Repository<Project> implements Filterable<Project> {

  public ProjectInMemoryDatabase() {
    entities = Seed.seedProjects();
  }

  public Project add(Project model) {
    String id = CustomUtils.getNextId("PJ", count());
    model.setId(id);
    return innerAdd(model);
  }

  @Override
  public Project[] filter(Predicate<Project> predicate) {
    return Arrays.stream(entities).filter(predicate).toArray(Project[]::new);
  }

}
