
package com.kratosgado.pms.models;

import com.kratosgado.pms.interfaces.Completable;
import com.kratosgado.pms.utils.TaskStatus;

public class Task implements Completable {
  private final String id;
  private String name;
  private TaskStatus status;
  private int hours;
  private String userId;
  private String projectId;

  public Task(final String id, final String name, final TaskStatus status) {
    // TODO: Auto-generated constructor stub
    this(id, name, status, null);
  }

  public Task(final String id, final String name, final String projectId) {
    this(id, name, TaskStatus.PENDING, projectId);
  }

  public Task(final String id, final String name, final TaskStatus status, final String projectId) {
    this.id = id;
    this.name = name;
    this.status = status;
    this.hours = 0;
    this.projectId = projectId;
  }

  public String getId() {
    return id;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public TaskStatus getStatus() {
    return status;
  }

  public void setStatus(final TaskStatus status) {
    this.status = status;
  }

  public int getHours() {
    return hours;
  }

  public void setHours(final int hours) {
    this.hours = hours;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String id) {
    this.userId = id;
  }

  @Override
  public String toString() {
    return String.format("%-20s|%-20s|%-20s|%-20s\n", id, name, getStatus(), getHours());
  }

  @Override
  public boolean isCompleted() {
    return status.equals(TaskStatus.COMPLETED);
  }
}
