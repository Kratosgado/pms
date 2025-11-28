package services;

import java.util.ArrayList;

import models.Task;
import utils.Console;
import utils.ConsoleMenu;
import utils.CustomUtils;
import utils.TaskStatus;
import utils.ValidationUtils;

public class TaskService extends MainService {
  private final ArrayList<Task> tasks;

  TaskService(final ArrayList<Task> tasks) {
    this.tasks = tasks;
    title = "TASK CATALOG";
  }

  private Task getTaskById(final String id) throws IllegalArgumentException {
    for (final Task task : tasks) {
      if (task.getId().equals(id)) {
        return task;
      }
    }
    throw new IllegalArgumentException("Task not found");
  }

  private void addTask() {
    ConsoleMenu.requireAdmin();
    ConsoleMenu.displayHeader("ADD TASK");
    String name;
    TaskStatus status;
    name = Console.getString("Enter Task Name: ");
    status = ConsoleMenu.getInput("Enter Initial Status (Pending, In Progress, Completed): ", input -> {
      return ValidationUtils.validateTaskStatus(input);
    });
    final Task task = new Task(CustomUtils.getNextId("T", tasks.size()), name, status);
    task.setHours(Console.getPositiveIntInput("Enter Hours: "));
    task.setUser(Console.getString("Enter id of user to be assigned: "));
    tasks.add(task);
    System.out.printf("✅Task '%s\' added successfully to project\n", task.getName());
  }

  private void updateTaskStatus() {
    ConsoleMenu.requireAdmin();
    ConsoleMenu.displayHeader("UDATE TASK STATUS");

    final String id = Console.getString("Enter Task ID: ");
    final Task task = getTaskById(id);
    final TaskStatus taskStatus = ConsoleMenu.getInput("Enter New Status (Pending, In Progress, Completed): ",
        input -> {
          return ValidationUtils.validateTaskStatus(input);
        });
    task.setStatus(taskStatus);
    tasks.set(tasks.indexOf(task), task);
    System.out.printf("✅Task '%s\' updated successfully as '%s'\n", task.getName(), taskStatus.getStatus());
  }

  private void removeTask() {
    ConsoleMenu.requireAdmin();
    ConsoleMenu.displayHeader("REMOVE TASK");
    final String id = Console.getString("Enter Task ID: ");
    final Task task = getTaskById(id);
    tasks.remove(task);
    System.out.println("✅Task Removed successfully");
  }

  private void listTasks() {
    ConsoleMenu.displayHeader("TASK LIST");
    final StringBuilder sb = new StringBuilder();
    ConsoleMenu.appendTableHeader(sb, String.format("%-20s|%-20s|%-20s|%-20s", "ID", "NAME", "STATUS", "HOURS"));
    for (final Task task : tasks) {
      sb.append(task);
    }
    sb.append("\n");
    System.out.println(sb);
  }

  @Override
  protected void displayOptions() {
    System.out.println("1. Add New Task");
    System.out.println("2. Update Task Status");
    System.out.println("3. Remove Task");
    System.out.println("4. View Tasks");
  }

  @Override
  public int handleChoice(final int choice) {
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
          listTasks();
          break;
        default:
          return choice;
      }
    } catch (final Exception e) {
      ConsoleMenu.displayError(e.getMessage());
    }
    return -1;
  }

}
