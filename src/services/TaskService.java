
package services;

import java.util.ArrayList;

import models.Task;
import utils.ConsoleMenu;
import utils.CustomUtils;
import utils.TaskStatus;
import utils.ValidationUtils;

public class TaskService extends MainService {
  private ArrayList<Task> tasks;

  TaskService(ArrayList<Task> tasks) {
    this.tasks = tasks;
  }

  private Task getTaskById(String id) throws IllegalArgumentException {
    for (Task task : tasks) {
      if (task.getId().equals(id)) {
        return task;
      }
    }
    throw new IllegalArgumentException("Task not found");
  }

  public void addTask() {
    System.out.println("Add Task");
    System.out.print("Enter Task Name: ");
    String name = ConsoleMenu.scanner.nextLine();
    System.out.print("Enter Initial Status (Pending, In Progress, Completed): ");
    String status = ConsoleMenu.scanner.nextLine();
    TaskStatus taskStatus = ValidationUtils.validateTaskStatus(status);
    Task task = new Task(CustomUtils.getNextId("T", tasks.size()), name, taskStatus);
    tasks.add(task);
  }

  public void updateTaskStatus() {
    System.out.println("||================================================================================||");
    System.out.println("|| Update Task Status");
    System.out.println("||================================================================================||");

    System.out.print("Enter Task ID: ");
    String id = ConsoleMenu.scanner.nextLine();
    Task task = getTaskById(id);
    System.out.println("Update Task Status");
    System.out.print("Enter New Status (Pending, In Progress, Completed): ");
    String status = ConsoleMenu.scanner.nextLine();
    TaskStatus taskStatus = ValidationUtils.validateTaskStatus(status);
    task.setStatus(taskStatus);
    tasks.set(tasks.indexOf(task), task);
    System.out.println("Task Status Updated");
  }

  public void removeTask() {
    System.out.println("Remove Task");
    System.out.print("Enter Task ID: ");
    String id = ConsoleMenu.scanner.nextLine();
    Task task = getTaskById(id);
    tasks.remove(task);
    System.out.println("Task Removed");
  }

  public void displayTasks() {
    System.out.println("||================================================================================||");
    System.out.println("|| Task List");
    System.out.println("||================================================================================||");
    for (Task task : tasks) {
      System.out.printf("|| %s (%s) || %s \n", task.getName(), task.getId(), task.getStatus().getStatus());
    }
    System.out.println("||================================================================================||");
  }

  @Override
  public void displayMenu() {
    System.out.println("1. Add New Task");
    System.out.println("2. Update Task Status");
    System.out.println("3. Remove Task");
    System.out.println("5. Exit");
  }

  @Override
  public int handleChoice(int choice) {
    try {
      switch (choice) {
        case 1:
          addTask();
          break;
        case 2:
          updateTaskStatus();
          break;
        case 3:
          removeTask();
          break;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return -1;
  }

}
