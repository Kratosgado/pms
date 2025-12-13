package com.kratosgado.pms.services;

import java.util.function.BiFunction;

import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.models.Task;
import com.kratosgado.pms.utils.Console;
import com.kratosgado.pms.utils.ConsoleMenu;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.ValidationUtils;
import com.kratosgado.pms.utils.context.AuthManager;
import com.kratosgado.pms.utils.enums.TaskStatus;
import com.kratosgado.pms.utils.exceptions.TaskNotFoundException;
import com.kratosgado.pms.utils.exceptions.UserNotFoundException;

public class TaskService extends ConsoleService {
  private final Project project;
  private final UserInMemoryDatabase usersDb;
  private final AuthManager authManager;

  public TaskService(Project project, UserInMemoryDatabase usersDb, AuthManager authManager) {
    this.project = project;
    this.usersDb = usersDb;
    this.authManager = authManager;
    title = "TASK CATALOG FOR PROJECT: " + project.getName();
  }

  private void addTask() {
    authManager.requireAdmin();
    CustomUtils.displayHeader("ADD TASK");
    String id = ConsoleMenu.getInput("Enter Task ID: ", ValidationUtils::validateTaskId);
    String name = Console.getString("Enter Task Name: ");
    TaskStatus status = ConsoleMenu.getInput("Enter Initial Status (Pending, In Progress, Completed): ",
        ValidationUtils::validateTaskStatus);
    final Task task = new Task(id, name, status);
    task.setHours(Console.getPositiveIntInput("Enter Hours: "));
    String userId = Console.getString("Enter id of user to be assigned (0 for no user): ");
    if (userId.equals("0")) {
      task.setUserId(null);
    } else if (!usersDb.exists(userId)) {
      throw new UserNotFoundException();
    } else {
      task.setUserId(userId);
    }
    project.addTask(task);
    System.out.printf("✅Task '%s\' added successfully to project\n", task.getName());
  }

  private void updateTaskStatus() {
    CustomUtils.displayHeader("UPDATE TASK STATUS");

    final String id = Console.getString("Enter Task ID: ");
    final Task task = project.findTaskById(id).orElseThrow(TaskNotFoundException::new);
    final TaskStatus taskStatus = ConsoleMenu.getInput("Enter New Status (Pending, In Progress, Completed): ",
        ValidationUtils::validateTaskStatus);
    task.setStatus(taskStatus);
    System.out.printf("✅Task '%s\' updated successfully as '%s'\n", task.getName(), taskStatus.getStatus());
  }

  private void removeTask() {
    authManager.requireAdmin();
    CustomUtils.displayHeader("REMOVE TASK");
    final String id = Console.getString("Enter Task ID: ");
    if (!project.removeTaskById(id)) {
      throw new TaskNotFoundException();
    }
    System.out.println("✅Task Removed successfully");
  }

  private void listTasks() {
    CustomUtils.displayHeader("TASK LIST");
    final StringBuilder sb = new StringBuilder();
    CustomUtils.appendTableHeader(sb, String.format("%-20s|%-20s|%-20s|%-20s", "ID", "NAME", "STATUS", "HOURS"));
    for (final Task task : project.getTasks()) {
      sb.append(task);
    }
    sb.append("\n");
    System.out.println(sb);
  }

  private void simulateConcurrentTaskUpdate() {
    CustomUtils.displayHeader("PARALLEL TASK UPDATE SIMULATION");
    System.out.println("Starting 3 threads to update tasks in parallel...");
    BiFunction<String, TaskStatus, Void> updateFunction = (id, status) -> {
      System.out.println(Thread.currentThread().getName() + " updating task " + id + " -> " + status);
      project.updateTaskStatus(id, status);
      return null;
    };
    Thread t1 = new Thread(() -> updateFunction.apply("TS001", TaskStatus.PENDING), "Thread 1");
    Thread t2 = new Thread(() -> updateFunction.apply("TS002", TaskStatus.IN_PROGRESS), "Thread 2");
    Thread t3 = new Thread(() -> updateFunction.apply("TS003", TaskStatus.COMPLETED), "Thread 3");
    t1.start();
    t2.start();
    t3.start();
    try {
      t1.join();
      t2.join();
      t3.join();
    } catch (InterruptedException e) {
      e.printStackTrace();

    }
    CustomUtils.displaySuccess("Task Update Simulation Completed");
  }

  @Override
  protected void displayOptions() {
    System.out.println("1. Add New Task");
    System.out.println("2. Update Task Status");
    System.out.println("3. Remove Task");
    System.out.println("4. View Tasks");
    System.out.println("5. Simulate Concurrent Task Update");
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
        case 5:
          simulateConcurrentTaskUpdate();
          break;
        default:
          return choice;
      }
    } catch (final Exception e) {
      CustomUtils.displayError(e.getClass().getSimpleName(), e.getMessage());
    }
    return -1;
  }

}
