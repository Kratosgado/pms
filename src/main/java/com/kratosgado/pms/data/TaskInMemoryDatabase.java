
package com.kratosgado.pms.data;

import java.util.Arrays;

import com.kratosgado.pms.models.Task;
import com.kratosgado.pms.utils.Seed;

public class TaskInMemoryDatabase extends Repository<Task> {
  private String projectId = "";

  public TaskInMemoryDatabase() {
    entities = Seed.seedTasks();
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
    return Arrays.stream(entities).filter(task -> task.getProjectId().equals(projectId)).toArray(Task[]::new);
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
