
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
  private ArrayList<Project> projects;
  private Project selectedProject;

  public ProjectService(ArrayList<Project> projects) {
    this.projects = projects;
    this.title = "PROJECT CATALOG";
  }

  private String listProjects(List<Project> projects) {
    ConsoleMenu.displayHeader("PROJECT LIST");
    StringBuilder sb = new StringBuilder();
    sb.append("Id\tName\t\t\t|Type\t\t|Description\t\t|Team Size\t\t|Budget\n");
    sb.append("--------------------------------------------------------------------------------\n");
    for (Project project : projects) {
      sb.append(project.toString());
    }
    sb.append("\n");
    return sb.toString();
  }

  private Project getProjectById(String id) {
    for (Project project : projects) {
      if (project.getId().equals(id)) {
        return project;
      }
    }
    throw new IllegalArgumentException("Project not found");
  }

  private void addProject() {
    ConsoleMenu.requireAdmin();
    ConsoleMenu.displayHeader("ADD PROJECT");
    String name = Console.getString("Enter Project Name: ");
    String description = Console.getString("Enter Project Description: ");
    int teamSize = Console.getPositiveIntInput("Enter Team Size: ");
    double budget = Console.getDoubleInput("Enter Budget: ");
    String type = Console.getString("Enter Project Type (soft for Software, hard for Hardware): ");
    Project project;
    String id = CustomUtils.getNextId("P", projects.size());
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
    String id = Console.getString("Enter Project ID: ");
    Project project = getProjectById(id);
    projects.remove(project);
    System.out.println("✅Project Removed successfully");
  }

  protected String listProjects() {
    return listProjects(projects);
  }

  private String listSoftwareProjects() {
    return listProjects(projects.stream().filter(project -> project instanceof SoftwareProject).toList());
  }

  private String listHardwareProjects() {
    return listProjects(projects.stream().filter(project -> project instanceof HardwareProject).toList());
  }

  private String searchByBudgetRange() {
    double min, max;
    min = Console.getDoubleInput("Enter Minimum Budget: ");
    max = Console.getDoubleInput("Enter Maximum Budget: ");
    return listProjects(
        projects.stream().filter(project -> project.getBudget() >= min && project.getBudget() <= max).toList());
  }

  protected void askForProject() {
    String id = ConsoleMenu.getInput("Enter project Id to view details (or 0 to return): ", input -> {
      return input;
    });
    if (id.equals("0"))
      return;
    selectedProject = getProjectById(id);
    System.out.println(selectedProject.getProjectDetails());
    ConsoleMenu.runningServices.add(new TaskService(selectedProject.getTasks()));
  }

  @Override
  protected void displayOptions() {
    System.out.println("1. Add Project");
    System.out.println("2. View  All Projects (" + projects.size() + ")");
    System.out.println("3. Software Projects Only");
    System.out.println("4. Hardware Projects Only");
    System.out.println("5. Search by Budget Range");
    System.out.println("6. Remove Project");

  }

  @Override
  public int handleChoice(int choice) {
    try {
      switch (choice) {
        case 1:
          addProject();
          break;
        case 2:
          System.out.println(listProjects());
          break;
        case 3:
          System.out.println(listSoftwareProjects());
          break;
        case 4:
          System.out.println(listHardwareProjects());
          break;
        case 5:
          System.out.println(searchByBudgetRange());
          break;
        case 6:
          removeProject();
          break;
        default:
          return choice;
      }
      askForProject();
    } catch (Exception e) {
      ConsoleMenu.displayError(e.getMessage());
    }
    return -1;
  }

}
