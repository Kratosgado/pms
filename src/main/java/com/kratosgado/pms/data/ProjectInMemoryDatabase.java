
package com.kratosgado.pms.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import com.kratosgado.pms.interfaces.Filterable;
import com.kratosgado.pms.interfaces.Persists;
import com.kratosgado.pms.models.HardwareProject;
import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.models.SoftwareProject;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.enums.ProjectType;
import com.kratosgado.pms.utils.factories.ModelFactory;

public class ProjectInMemoryDatabase extends Repository<Project> implements Filterable<Project>, Persists {
  private final String fileName;

  public ProjectInMemoryDatabase() {
    this.fileName = "projects.json";
  }

  public ProjectInMemoryDatabase(String fileName) {
    this.fileName = fileName;
  }

  public ProjectInMemoryDatabase(HashMap<String, Project> entities) {
    super(entities);
    this.fileName = "projects.json";
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

  @Override
  public void saveData() throws IOException {
    Files.writeString(Path.of(fileName),
        entities.values().stream().map(project -> project.toJson()).toList().toString());

  }

  @Override
  public boolean dataExists() {
    return Files.exists(Path.of(fileName));

  }

  @Override
  public void loadData() throws IOException {
    String json = readFile(fileName);
    entities = new HashMap<>();
    for (String projectStr : json.split("]},")) {
      if (projectStr.equals("[]"))
        continue;
      Project project;
      if (Project.getProjectType(projectStr).equals("HARDWARE")) {
        project = HardwareProject.fromJson(projectStr);
        entities.put(project.getId(), project);
        continue;
      }
      project = SoftwareProject.fromJson(projectStr);
      entities.put(project.getId(), project);
    }
  }

}
