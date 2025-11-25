
package services;

import models.Task;

public class TaskService extends MainService {

  private Task[] tasks;

  TaskService(Task[] tasks) {
    this.tasks = tasks;
  }

}
