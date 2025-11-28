
package models;

import interfaces.Completable;
import utils.TaskStatus;

public class Task implements Completable {
  private String id;
  private String name;
  private TaskStatus status;
  private String userID;

  public Task(String id, String name) {
    this(id, name, TaskStatus.PENDING);
  }

  public Task(String id, String name, TaskStatus status) {
    this.id = id;
    this.name = name;
    this.status = status;
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

  public void setStatus(TaskStatus status) {
    this.status = status;
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public String toString() {
    return String.format("%-20s|%-20s|%-20s\n", id, name, status.getStatus());
  }

  @Override
  public boolean isCompleted() {
    return status == TaskStatus.COMPLETED;
  }
}
