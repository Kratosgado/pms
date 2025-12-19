
package com.kratosgado.pms.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import com.kratosgado.pms.interfaces.Filterable;
import com.kratosgado.pms.interfaces.Persists;
import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.enums.ProjectType;
import com.kratosgado.pms.utils.exceptions.ConflictException;
import com.kratosgado.pms.utils.factories.ModelFactory;

public class ProjectInMemoryDatabase extends Repository<Project> implements Filterable<Project>, Persists {
  private final String fileName;

  public ProjectInMemoryDatabase() {
    this.fileName = "data/projects.json";
  }

  /**
   * @param fileName File to save and load data from
   * @param entities Entiies to seed the database
   */

  public ProjectInMemoryDatabase(String fileName) {
    this.fileName = fileName;
  }

  /**
   * @param fileName File to save and load data from
   * @param entities Entiies to seed the database
   */
  public ProjectInMemoryDatabase(String fileName, HashMap<String, Project> entities) {
    this.fileName = fileName;
    if (!fileExists())
      this.entities = entities;

  }

  public Project add(String name, String description, int teamSize, double budget, ProjectType type)
      throws ConflictException {
    String id = CustomUtils.getNextId("PJ", count());
    return safeAdd(ModelFactory.createProject(id, name, description, teamSize, budget, type));
  }

  @Override
  public List<Project> filter(Predicate<Project> predicate) {
    return entities.values().stream().filter(predicate).toList();

  }

  @Override
  public Project add(Project model) throws ConflictException {
    return safeAdd(model);
  }

  @Override
  public void saveData() throws IOException {
    Files.writeString(Path.of(fileName),
        entities.values().stream().map(project -> project.toJson()).toList().toString());

    System.out.println("Saved " + count() + " projects" + " to " + fileName);
  }

  @Override
  public boolean fileExists() {
    return Files.exists(Path.of(fileName));

  }

  @Override
  public void loadData() throws IOException {

    String json = readFile(fileName);
    entities = new HashMap<>();
    if (json.contains("id"))
      for (String projectStr : json.split("]\n},")) {
        Project project = ModelFactory.createProjectFromJson(projectStr);
        entities.put(project.getId(), project);
      }

    System.out.println("Loaded " + count() + " projects" + " from " + fileName);
  }

}
