
package services;

import java.util.ArrayList;
import java.util.List;

import models.HardwareProject;
import models.Project;
import models.SoftwareProject;
import utils.Console;
import utils.ConsoleMenu;
import utils.CustomUtils;

public class ProjectService extends MainService {
  private final ArrayList<Project> projects;
  private Project selectedProject;

  public ProjectService(final ArrayList<Project> projects) {
    this.projects = projects;
    this.title = "PROJECT CATALOG";
  }

  protected void listProjects() {
    System.out.println(listProjects(projects));
  }

  private String listProjects(final List<Project> projects) {
    ConsoleMenu.displayHeader("PROJECT LIST");
    final StringBuilder sb = new StringBuilder();
    ConsoleMenu.appendTableHeader(sb,
        String.format("%-20s|%-20s|%-20s|%-20s|%-20s", "ID", "NAME", "TYPE", "TEAM SIZE", "BUDGET"));
    for (final Project project : projects) {
      sb.append(project);
    }
    sb.append("\n");
    return sb.toString();
  }

  private Project getProjectById(final String id) {
    for (final Project project : projects) {
      if (project.getId().equals(id)) {
        return project;
      }
    }
    throw new IllegalArgumentException("Project not found");
  }

  private void addProject() {
    ConsoleMenu.requireAdmin();
    ConsoleMenu.displayHeader("ADD PROJECT");
    final String name = Console.getString("Enter Project Name: ");
    final String description = Console.getString("Enter Project Description: ");
    final int teamSize = Console.getPositiveIntInput("Enter Team Size: ");
    final double budget = Console.getDoubleInput("Enter Budget: ");
    final String type = Console.getString("Enter Project Type (soft for Software, hard for Hardware): ");
    Project project;
    final String id = CustomUtils.getNextId("PJ", projects.size());
    if (type.equals("soft"))
      project = new SoftwareProject(id, name, description, teamSize, budget);
    else if (type.equals("hard"))
      project = new HardwareProject(id, name, description, teamSize, budget);
    else
      throw new IllegalArgumentException("Invalid Project type");
    projects.add(project);
    System.out.printf("✅Project '%s\' added successfully with id '%s'\n", project.getName(), project.getId());
  }

  private void removeProject() {
    ConsoleMenu.requireAdmin();
    ConsoleMenu.displayHeader("REMOVE PROJECT");
    final String id = Console.getString("Enter Project ID: ");
    final Project project = getProjectById(id);
    projects.remove(project);
    System.out.println("✅Project Removed successfully");
  }

  private void listSoftwareProjects() {
    System.out.println(listProjects(projects.stream().filter(project -> project instanceof SoftwareProject).toList()));
  }

  private void listHardwareProjects() {
    System.out.println(listProjects(projects.stream().filter(project -> project instanceof HardwareProject).toList()));
  }

  private void searchByBudgetRange() {
    double min, max;
    min = Console.getDoubleInput("Enter Minimum Budget: ");
    max = Console.getDoubleInput("Enter Maximum Budget: ");
    System.out.println(listProjects(
        projects.stream().filter(project -> project.getBudget() >= min && project.getBudget() <= max).toList()));
  }

  protected void askForProject() {
    final String id = ConsoleMenu.getInput("Enter project Id to view details (or 0 to return): ", input -> {
      return input;
    });
    if (id.equals("0"))
      return;
    selectedProject = getProjectById(id);
    System.out.println(selectedProject.getProjectDetails());
    ConsoleMenu.runningServices.add(new TaskService(selectedProject.getTasks()));
  }

  private void calculateProjectCompletion() {
    ConsoleMenu.displayHeader("CALCULATE PROJECT COMPLETION");
    final String id = Console.getString("Enter Project ID: ");
    final Project project = getProjectById(id);
    final double progress = project.getProgress();
    System.out.printf("✅Project '%s\' completed with progress %.2f%%\n", project.getName(), progress);
  }

  @Override
  protected void displayOptions() {
    System.out.println("1. Add Project");
    System.out.println("2. View  All Projects (" + projects.size() + ")");
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
