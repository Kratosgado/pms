package com.kratosgado.pms.services;

import com.kratosgado.pms.data.TaskInMemoryDatabase;
import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.models.Task;
import com.kratosgado.pms.utils.Console;
import com.kratosgado.pms.utils.ConsoleMenu;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.TaskStatus;
import com.kratosgado.pms.utils.ValidationUtils;

public class TaskService extends MainService {
  private final TaskInMemoryDatabase tasksDb;
  private final UserInMemoryDatabase usersDb;

  TaskService(final TaskInMemoryDatabase tasks, final UserInMemoryDatabase usersDb) {
    this.usersDb = usersDb;
    this.tasksDb = tasks;
    title = "TASK CATALOG";
  }

  private void addTask() {
    ConsoleMenu.requireAdmin();
    CustomUtils.displayHeader("ADD TASK");
    String name;
    TaskStatus status;
    name = Console.getString("Enter Task Name: ");
    status = ConsoleMenu.getInput("Enter Initial Status (Pending, In Progress, Completed): ", input -> {
      return ValidationUtils.validateTaskStatus(input);
    });
    final Task task = new Task(CustomUtils.getNextId("TS", tasksDb.count()), name, status);
    task.setHours(Console.getPositiveIntInput("Enter Hours: "));
    task.setUserId(Console.getString("Enter id of user to be assigned: "));
    tasksDb.add(task);
    System.out.printf("✅Task '%s\' added successfully to project\n", task.getName());
  }

  private void updateTaskStatus() {
    ConsoleMenu.requireAdmin();
    CustomUtils.displayHeader("UDATE TASK STATUS");

    final String id = Console.getString("Enter Task ID: ");
    final Task task = tasksDb.getById(id);
    final TaskStatus taskStatus = ConsoleMenu.getInput("Enter New Status (Pending, In Progress, Completed): ",
        input -> {
          return ValidationUtils.validateTaskStatus(input);
        });
    task.setStatus(taskStatus);
    tasksDb.update(task);
    System.out.printf("✅Task '%s\' updated successfully as '%s'\n", task.getName(), taskStatus.getStatus());
  }

  private void removeTask() {
    ConsoleMenu.requireAdmin();
    CustomUtils.displayHeader("REMOVE TASK");
    final String id = Console.getString("Enter Task ID: ");
    tasksDb.removeById(id);
    System.out.println("✅Task Removed successfully");
  }

  private void listTasks() {
    CustomUtils.displayHeader("TASK LIST");
    final StringBuilder sb = new StringBuilder();
    CustomUtils.appendTableHeader(sb, String.format("%-20s|%-20s|%-20s|%-20s", "ID", "NAME", "STATUS", "HOURS"));
    for (final Task task : tasksDb.getAll()) {
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
