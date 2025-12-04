
package com.kratosgado.pms.data;

import java.util.ArrayList;

import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.Seed;

public class ProjectInMemoryDatabase extends Repository<Project> {

  public ProjectInMemoryDatabase() {
    entities = Seed.seedProjects();
  }

  public Project add(Project model) {
    String id = CustomUtils.getNextId("PJ", count());
    model.setId(id);
    return innerAdd(model);
  }

  public Project[] getSoftwareProjects() {
    ArrayList<Project> projects = new ArrayList<>();
    for (Project project : entities) {
      if (project.getProjectType().equals("Software"))
        projects.add(project);
    }
    return projects.toArray(new Project[0]);
  }

  public Project[] getHardwareProjects() {
    ArrayList<Project> projects = new ArrayList<>();
    for (Project project : entities) {
      if (project.getProjectType().equals("Hardware"))
        projects.add(project);
    }
    return projects.toArray(new Project[0]);
  }

  public Project[] getBudgetRangeProjects(double min, double max) {
    ArrayList<Project> projects = new ArrayList<>();
    for (Project project : entities) {
      if (project.getBudget() >= min && project.getBudget() <= max)
        projects.add(project);
    }
    return projects.toArray(new Project[0]);
  }

}
