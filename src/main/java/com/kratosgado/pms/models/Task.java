
package com.kratosgado.pms.models;

import com.kratosgado.pms.interfaces.Completable;
import com.kratosgado.pms.interfaces.HasId;
import com.kratosgado.pms.interfaces.JsonSerializable;
import com.kratosgado.pms.utils.enums.TaskStatus;

public class Task implements HasId, Completable, JsonSerializable {
  private final String id;
  private String name;
  private TaskStatus status;
  private int hours;
  private String userId;
  private String projectId;

  public Task(final String id, final String name, final TaskStatus status) {
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

  @Override
  public String toJson() {
    return String.format(
        "{\"id\":\"%s\",\"name\":\"%s\",\"status\":\"%s\",\"hours\":%s,\"userId\":\"%s\"}", id,
        name, status, hours, userId);
  }

  public Task fromJson(String json) {
    int idStart = json.indexOf("\"id\":\"") + 6;
    int idEnd = json.indexOf("\",", idStart + 1);
    String id = json.substring(idStart, idEnd);

    int nameStart = json.indexOf("\"name\":\"") + 8;
    int nameEnd = json.indexOf("\",", nameStart + 1);
    String name = json.substring(nameStart, nameEnd);

    int statusStart = json.indexOf("\"status\":\"") + 10;
    int statusEnd = json.indexOf("\",", statusStart + 1);
    String status = json.substring(statusStart, statusEnd);

    int hoursStart = json.indexOf("\"hours\":") + 8;
    int hoursEnd = json.indexOf(",", hoursStart + 1);
    String hours = json.substring(hoursStart, hoursEnd);

    int userIdStart = json.indexOf("\"userId\":\"") + 10;
    int userIdEnd = json.indexOf("\",", userIdStart + 1);
    String userId = json.substring(userIdStart, userIdEnd);

    Task task = new Task(id, name, TaskStatus.valueOf(status), userId);
    task.setHours(Integer.parseInt(hours));
    return task;
  }

}
