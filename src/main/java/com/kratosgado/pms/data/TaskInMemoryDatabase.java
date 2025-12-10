
package com.kratosgado.pms.data;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import com.kratosgado.pms.interfaces.Filterable;
import com.kratosgado.pms.models.Task;

public class TaskInMemoryDatabase extends Repository<Task> implements Filterable<Task> {
  private String projectId = "";

  public TaskInMemoryDatabase() {
  }

  public TaskInMemoryDatabase(HashMap<String, Task> entities) {
    super(entities);
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  @Override
  public Task add(Task model) {
    model.setProjectId(projectId);
    return safeAdd(model);
  }

  @Override
  public List<Task> filter(Predicate<Task> predicate) {
    return entities.values().stream().filter(predicate).toList();
  }

}
