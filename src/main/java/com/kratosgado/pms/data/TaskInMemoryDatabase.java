
package com.kratosgado.pms.data;

import java.util.ArrayList;

import com.kratosgado.pms.interfaces.InMemoryDatabase;
import com.kratosgado.pms.models.Task;

public class TaskInMemoryDatabase implements InMemoryDatabase<Task> {
  private ArrayList<Task> tasks;
  private String projectId = "";

  public TaskInMemoryDatabase() {
    tasks = new ArrayList<>();
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  @Override
  public Task add(Task model) {
    tasks.add(model);
    return model;
  }

  @Override
  public Task update(Task model) {
    final Task task = getById(model.getId());
    task.setName(model.getName());
    task.setStatus(model.getStatus());
    task.setHours(model.getHours());
    task.setUserId(model.getUserId());
    return task;
  }

  @Override
  public ArrayList<Task> getAll() {
    return new ArrayList<>(tasks.stream().filter(ts -> ts.getProjectId().equals(projectId)).toList());
  }

  @Override
  public Task getById(String id) {
    for (final Task task : tasks) {
      if (task.getId().equals(id)) {
        return task;
      }
    }
    // TODO: replace with custom exception
    throw new IllegalArgumentException("Task not found");
  }

  @Override
  public Task getWhere(String field, String value) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getWhere'");
  }

  @Override
  public void removeById(String id) {
    Task task = getById(id);
    tasks.remove(task);

  }

  @Override
  public int count() {
    return (int) tasks.stream().filter(ts -> ts.getProjectId().equals(projectId)).count();
  }
}
