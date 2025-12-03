
package com.kratosgado.pms.models;

import com.kratosgado.pms.interfaces.Completable;
import com.kratosgado.pms.utils.TaskStatus;

public class Task implements Completable {
  private final String id;
  private final String name;
  private TaskStatus status;
  private int hours;
  private String userId;

  public Task(final String id, final String name) {
    this(id, name, TaskStatus.PENDING);
  }

  public Task(final String id, final String name, final TaskStatus status) {
    this.id = id;
    this.name = name;
    this.status = status;
    this.hours = 0;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
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
