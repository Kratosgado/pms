
package com.kratosgado.pms.data;

import java.util.ArrayList;
import java.util.function.Predicate;

import com.kratosgado.pms.interfaces.Filterable;
import com.kratosgado.pms.models.Task;

public class TaskInMemoryDatabase extends Repository<Task> implements Filterable<Task> {
  private String projectId = "";

  public TaskInMemoryDatabase() {
  }

  public TaskInMemoryDatabase(Task[] entities) {
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
  public Task[] getAll() {
    ArrayList<Task> tasks = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      if ((entities[i]).getProjectId().equals(projectId)) {
        tasks.add(entities[i]);
      }
    }
    Task[] result = new Task[tasks.size()];
    tasks.toArray(result);
    return result;
  }

  @Override
  public Task[] filter(Predicate<Task> predicate) {
    ArrayList<Task> tasks = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      if (predicate.test(entities[i])) {
        tasks.add(entities[i]);
      }
    }
    Task[] result = new Task[tasks.size()];
    tasks.toArray(result);
    return result;
  }

}
