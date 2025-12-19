
package com.kratosgado.pms.services;

import java.util.List;

import com.kratosgado.pms.data.ProjectInMemoryDatabase;
import com.kratosgado.pms.interfaces.ServiceFactory;
import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.utils.Console;
import com.kratosgado.pms.utils.ConsoleMenu;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.ValidationUtils;
import com.kratosgado.pms.utils.context.AuthManager;
import com.kratosgado.pms.utils.context.NavigationManager;
import com.kratosgado.pms.utils.enums.ProjectType;
import com.kratosgado.pms.utils.exceptions.EntityNotFoundException;
import com.kratosgado.pms.utils.exceptions.ProjectNotFoundException;

public class ProjectService extends ConsoleService {
  private final ProjectInMemoryDatabase projectsDb;
  private final ServiceFactory serviceFactory;
  private final AuthManager authManager;
  private final NavigationManager navigationManager;

  public ProjectService(ProjectInMemoryDatabase projectsDb, AuthManager authManager,
      NavigationManager navigationManager, ServiceFactory serviceFactory) {
    this.projectsDb = projectsDb;
    this.title = "PROJECT CATALOG";
    this.serviceFactory = serviceFactory;
    this.authManager = authManager;
    this.navigationManager = navigationManager;
  }

  protected void listProjects() {
    System.out.println(listProjects(projectsDb.getAll()));
    askForProject();
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
    authManager.requireAdmin();
    CustomUtils.displayHeader("ADD PROJECT");
    final String name = Console.getString("Enter Project Name: ");
    final String description = Console.getString("Enter Project Description: ");
    final int teamSize = Console.getPositiveIntInput("Enter Team Size: ");
    final double budget = Console.getDoubleInput("Enter Budget: ");
    final ProjectType type = ConsoleMenu.getInput("Enter Project Type (Software/Hardware): ", input -> {
      return ValidationUtils.validateProjectType(input);
    });
    final Project project = projectsDb.add(name, description, teamSize, budget, type);
    System.out.printf("✅Project '%s\' added successfully with id '%s'\n", project.getName(), project.getId());
  }

  private void removeProject() {
    authManager.requireAdmin();
    CustomUtils.displayHeader("REMOVE PROJECT");
    final String id = Console.getString("Enter Project ID: ");
    try {
      projectsDb.removeById(id);
      System.out.println("✅Project Removed successfully");
    } catch (EntityNotFoundException e) {
      CustomUtils.displayError(new ProjectNotFoundException());
    }

  }

  private void listSoftwareProjects() {
    System.out.println(
        listProjects(projectsDb.filter(project -> project.getProjectType().equals(ProjectType.SOFTWARE))));
  }

  private void listHardwareProjects() {
    System.out.println(
        listProjects(projectsDb.filter(project -> project.getProjectType().equals(ProjectType.HARDWARE))));
  }

  private void searchByBudgetRange() {
    double min, max;
    min = Console.getDoubleInput("Enter Minimum Budget: ");
    max = Console.getDoubleInput("Enter Maximum Budget: ");
    System.out
        .println(listProjects(projectsDb.filter(project -> project.getBudget() >= min && project.getBudget() <= max)));
  }

  private void displayProjectDetails(final String id) throws EntityNotFoundException {
    final Project project = projectsDb.getById(id);
    CustomUtils.displayHeader("PROJECT DETAILS: " + id);
    System.out.println(project.getProjectDetails());
  }

  protected void askForProject() {
    final String id = Console.getString("Enter project Id to view details (or 0 to return): ");
    if (id.equals("0"))
      return;
    try {
      this.displayProjectDetails(id);
      TaskService taskService = serviceFactory.createTaskService(id);
      navigationManager.pushService(taskService);

    } catch (EntityNotFoundException e) {
      CustomUtils.displayError(new ProjectNotFoundException());
    }
  }

  private void calculateProjectCompletion() {
    CustomUtils.displayHeader("CALCULATE PROJECT COMPLETION");
    final String id = Console.getString("Enter Project ID: ");
    try {
      final Project project = projectsDb.getById(id);
      final double progress = project.calculateCompletionPercentage();
      System.out.printf("✅Project '%s\' completed with progress %.2f%%\n", project.getName(), progress);
    } catch (EntityNotFoundException e) {
      CustomUtils.displayError(new ProjectNotFoundException());
    }
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
    } catch (final Exception e) {
      CustomUtils.displayError(e);
    }
    return -1;
  }

}
