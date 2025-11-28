
package models;

import interfaces.Completable;
import services.UserService;
import utils.TaskStatus;

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

  public User getUser() {
    return UserService.getUserById(userId);
  }

  public void setUser(String id) {
    UserService.getUserById(id);
    this.userId = id;
  }

  @Override
  public String toString() {
    return String.format("%-20s|%-20s|%-20s|%-20s\n", id, name, status.getStatus(), getHours());
  }

  @Override
  public boolean isCompleted() {
    return status == TaskStatus.COMPLETED;
  }
}
