
package com.kratosgado.pms.data;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

import com.kratosgado.pms.interfaces.Filterable;
import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.enums.ProjectType;
import com.kratosgado.pms.utils.factories.ModelFactory;

public class ProjectInMemoryDatabase extends Repository<Project> implements Filterable<Project> {
  private final TaskInMemoryDatabase tasksDb;

  public ProjectInMemoryDatabase(TaskInMemoryDatabase tasksDb) {
    this.tasksDb = tasksDb;
  }

  public ProjectInMemoryDatabase(TaskInMemoryDatabase tasksDb, Project[] entities) {
    super(entities);
    this.tasksDb = tasksDb;
  }

  @Override
  public Optional<Project> getById(String id) {
    Project project = super.getById(id).orElseThrow();
    tasksDb.setProjectId(project.getId());
    project.setTasks(tasksDb.getAll());
    return Optional.of(project);
  }

  @Override
  public Project[] getAll() {
    Project[] result = super.getAll();
    for (int i = 0; i < size; i++) {
      tasksDb.setProjectId(result[i].getId());
      entities[i].setTasks(tasksDb.getAll());
    }
    return result;
  }

  public Project add(String name, String description, int teamSize, double budget, ProjectType type) {
    String id = CustomUtils.getNextId("PJ", count());
    return safeAdd(ModelFactory.createProject(id, name, description, teamSize, budget, type));
  }

  @Override
  public Project[] filter(Predicate<Project> predicate) {
    ArrayList<Project> projects = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      if (predicate.test(entities[i])) {
        tasksDb.setProjectId(entities[i].getId());
        entities[i].setTasks(tasksDb.getAll());
        projects.add(entities[i]);
      }
    }
    Project[] result = new Project[projects.size()];
    projects.toArray(result);
    return result;
  }

  @Override
  public Project add(Project model) {
    return safeAdd(model);
  }

}
