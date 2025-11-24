
package models;

import interfaces.Completable;
import utils.TaskStatus;

public class Task implements Completable {

  private String id;
  private String name;
  private TaskStatus status;
  private int progress;
  private String userID;

  Task(String id, String name) {
    this.id = id;
    this.name = name;
    this.status = TaskStatus.PENDING;
  }

  Task(String id, String name, TaskStatus status) {
    this.id = id;
    this.name = name;
    this.status = status;
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
    return String.format("%s | %s | %s", id, name, status);
  }

  @Override
  public boolean isCompleted() {
    return status == TaskStatus.COMPLETED;
  }
}
