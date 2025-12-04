
package com.kratosgado.pms.data;

import java.util.ArrayList;

import com.kratosgado.pms.models.Task;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.Seed;

public class TaskInMemoryDatabase extends Repository<Task> {
  private String projectId = "";

  public TaskInMemoryDatabase() {
    entities = Seed.seedTasks("PJ000");
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  @Override
  public Task add(Task model) {
    model.setId(CustomUtils.getNextId("TS", count()));
    model.setProjectId(projectId);
    return innerAdd(model);
  }

  @Override
  public Task[] getAll() {
    ArrayList<Task> tasks = new ArrayList<>();
    for (Task task : entities) {
      if (task.getProjectId().equals(projectId))
        tasks.add(task);
    }
    return tasks.toArray(Task[]::new);
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
