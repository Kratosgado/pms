
package com.kratosgado.pms.services;

import java.util.ArrayList;
import java.util.List;

import com.kratosgado.pms.data.ProjectInMemoryDatabase;
import com.kratosgado.pms.data.TaskInMemoryDatabase;
import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.models.HardwareProject;
import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.models.SoftwareProject;
import com.kratosgado.pms.utils.ConsoleMenu;
import com.kratosgado.pms.utils.Console;
import com.kratosgado.pms.utils.CustomUtils;

public class ProjectService extends MainService {
  private final ProjectInMemoryDatabase projectsDb;
  private final TaskInMemoryDatabase tasksDb;
  private final UserInMemoryDatabase usersDb;
  private Project selectedProject;

  public ProjectService(final ProjectInMemoryDatabase projectsDb, final TaskInMemoryDatabase tasksDb,
      UserInMemoryDatabase usersDb) {
    this.projectsDb = projectsDb;
    this.tasksDb = tasksDb;
    this.title = "PROJECT CATALOG";
    this.usersDb = usersDb;
  }

  protected void listProjects() {
    System.out.println(listProjects(projectsDb.getAll()));
  }

  private String listProjects(final List<Project> projects) {
    CustomUtils.displayHeader("PROJECT LIST");
    final StringBuilder sb = new StringBuilder();
    CustomUtils.appendTableHeader(sb,
        String.format("%-20s|%-20s|%-20s|%-20s|%-20s", "ID", "NAME", "TYPE", "TEAM SIZE", "BUDGET"));
    for (final Project project : projects) {
      sb.append(project);
    }
    sb.append("\n");
    return sb.toString();
  }

  private void addProject() {
    ConsoleMenu.requireAdmin();
    CustomUtils.displayHeader("ADD PROJECT");
    final String name = Console.getString("Enter Project Name: ");
    final String description = Console.getString("Enter Project Description: ");
    final int teamSize = Console.getPositiveIntInput("Enter Team Size: ");
    final double budget = Console.getDoubleInput("Enter Budget: ");
    final String type = Console.getString("Enter Project Type (soft for Software, hard for Hardware): ");
    Project project;
    final String id = CustomUtils.getNextId("PJ", projectsDb.count());
    if (type.equals("soft"))
      project = new SoftwareProject(id, name, description, teamSize, budget);
    else if (type.equals("hard"))
      project = new HardwareProject(id, name, description, teamSize, budget);
    else
      throw new IllegalArgumentException("Invalid Project type");
    projectsDb.add(project);
    System.out.printf("✅Project '%s\' added successfully with id '%s'\n", project.getName(), project.getId());
  }

  private void removeProject() {
    ConsoleMenu.requireAdmin();
    CustomUtils.displayHeader("REMOVE PROJECT");
    final String id = Console.getString("Enter Project ID: ");
    projectsDb.removeById(id);
    System.out.println("✅Project Removed successfully");
  }

  private void listSoftwareProjects() {
    System.out.println(
        listProjects(
            projectsDb.getAll().stream().filter(project -> project.getProjectType().equals("Software")).toList()));
  }

  private void listHardwareProjects() {
    System.out.println(
        listProjects(
            projectsDb.getAll().stream().filter(project -> project.getProjectType().equals("Hardware")).toList()));
  }

  private void searchByBudgetRange() {
    double min, max;
    min = Console.getDoubleInput("Enter Minimum Budget: ");
    max = Console.getDoubleInput("Enter Maximum Budget: ");
    System.out.println(listProjects(
        projectsDb.getAll().stream().filter(project -> project.getBudget() >= min && project.getBudget() <= max)
            .toList()));
  }

  private void displayProjectDetails(final String id) {
    final Project project = projectsDb.getById(id);
    CustomUtils.displayHeader("PROJECT DETAILS: " + id);

    System.out.println(project.getProjectDetails());
  }

  protected void askForProject() {
    final String id = ConsoleMenu.getInput("Enter project Id to view details (or 0 to return): ", input -> {
      return input;
    });
    if (id.equals("0"))
      return;
    selectedProject = projectsDb.getById(id);
    System.out.println(selectedProject.getProjectDetails());
    tasksDb.setProjectId(id);
    TaskService taskService = new TaskService(tasksDb, usersDb);
    ConsoleMenu.runningServices.add(taskService);
  }

  private void calculateProjectCompletion() {
    CustomUtils.displayHeader("CALCULATE PROJECT COMPLETION");
    final String id = Console.getString("Enter Project ID: ");
    final Project project = projectsDb.getById(id);
    final double progress = project.calculateCompletionPercentage();
    System.out.printf("✅Project '%s\' completed with progress %.2f%%\n", project.getName(), progress);
  }

  @Override
  protected void displayOptions() {
    System.out.println("1. Add Project");
    System.out.println("2. View  All Projects (" + projectsDb.count() + ")");
    System.out.println("3. Software Projects Only");
    System.out.println("4. Hardware Projects Only");
    System.out.println("5. Search by Budget Range");
    System.out.println("6. Calculate Project Completion");
    System.out.println("7. Remove Project");

  }

  @Override
  public int handleChoice(final int choice) {
    try {
      switch (choice) {
        case 1:
          addProject();
          break;
        case 2:
          listProjects();
          break;
        case 3:
          listSoftwareProjects();
          break;
        case 4:
          listHardwareProjects();
          break;
        case 5:
          searchByBudgetRange();
          break;
        case 6:
          calculateProjectCompletion();
          break;
        case 7:
          removeProject();
          break;
        default:
          return choice;
      }
      askForProject();
    } catch (final Exception e) {
      ConsoleMenu.displayError(e.getMessage());
    }
    return -1;
  }

}
