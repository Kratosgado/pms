
package models;

import utils.TaskStatus;

public class Task {

  private String name;
  private String description;
  private TaskStatus status;
  private Project project;
  private String userID;

  Task(String name, String description, Project project) {
    this.name = name;
    this.description = description;
    this.project = project;
    this.status = TaskStatus.PENDING;
  }

  Task(String name, String description, TaskStatus status, Project project) {
    this.name = name;
    this.description = description;
    this.status = status;
    this.project = project;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public TaskStatus getStatus() {
    return status;
  }

  public void setStatus(TaskStatus status) {
    this.status = status;
  }

  public Project getProject() {
    return project;
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public String toString() {
    return String.format("Task: %s\nDescription: %s\nStatus: %s\nProject: %s", name, description, status, project);
  }
}
