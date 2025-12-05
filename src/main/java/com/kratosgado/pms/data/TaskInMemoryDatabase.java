
package com.kratosgado.pms.data;

import java.util.ArrayList;

import com.kratosgado.pms.models.Task;

public class TaskInMemoryDatabase extends Repository<Task> {
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

  // @Override
  // public Task update(Task model) {
  // final Task task = getById(model.getId());
  // task.setName(model.getName());
  // task.setStatus(model.getStatus());
  // task.setHours(model.getHours());
  // task.setUserId(model.getUserId());
  // return task;
  // }
}
