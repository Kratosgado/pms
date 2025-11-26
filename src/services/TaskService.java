package services;

import java.util.ArrayList;

import models.Task;
import utils.Console;
import utils.ConsoleMenu;
import utils.CustomUtils;
import utils.TaskStatus;
import utils.ValidationUtils;

public class TaskService extends MainService {
  private ArrayList<Task> tasks;

  TaskService(ArrayList<Task> tasks) {
    this.tasks = tasks;
    title = "TASK CATALOG";
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
    String name;
    TaskStatus status;
    name = Console.getString("Enter Task Name: ");
    status = ConsoleMenu.getInput("Enter Initial Status (Pending, In Progress, Completed): ", input -> {
      return ValidationUtils.validateTaskStatus(input);
    });
    Task task = new Task(CustomUtils.getNextId("T", tasks.size()), name, status);
    tasks.add(task);
    System.out.printf("✅Task '%s\' added successfully to project\n", task.getName());
  }

  public void updateTaskStatus() {
    System.out.println("||================================================================================||");
    System.out.println("|| Update Task Status");
    System.out.println("||================================================================================||");

    String id = Console.getString("Enter Task ID: ");
    Task task = getTaskById(id);
    TaskStatus taskStatus = ConsoleMenu.getInput("Enter New Status (Pending, In Progress, Completed): ", input -> {
      return ValidationUtils.validateTaskStatus(input);
    });
    task.setStatus(taskStatus);
    tasks.set(tasks.indexOf(task), task);
    System.out.printf("✅Task '%s\' updated successfully as '%s'\n", task.getName(), taskStatus.getStatus());
  }

  public void removeTask() {
    System.out.println("Remove Task");
    String id = Console.getString("Enter Task ID: ");
    Task task = getTaskById(id);
    tasks.remove(task);
    System.out.println("✅Task Removed successfully");
  }

  public void displayTasks() {
    System.out.println("||================================================================================||");
    System.out.println("|| Task List");
    System.out.println("||================================================================================||\n");
    for (Task task : tasks) {
      System.out.printf("|| %s (%s) || %s \n", task.getName(), task.getId(), task.getStatus().getStatus());
    }
    System.out.println("||================================================================================||");
  }

  @Override
  void displayOptions() {
    System.out.println("1. Add New Task");
    System.out.println("2. Update Task Status");
    System.out.println("3. Remove Task");
    System.out.println("4. View Tasks");
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
        case 4:
          displayTasks();
          break;
        default:
          return choice;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return -1;
  }

}
