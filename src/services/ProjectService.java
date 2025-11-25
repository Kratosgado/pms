
package services;

import java.util.ArrayList;
import java.util.List;

import models.HardwareProject;
import models.Project;
import models.SoftwareProject;
import utils.ConsoleMenu;

public class ProjectService extends MainService {
  private ArrayList<Project> projects;

  public ProjectService(ArrayList<Project> projects) {
    this.projects = projects;
  }

  private String listProjects(List<Project> projects) {
    StringBuilder sb = new StringBuilder();
    sb.append("Projects List\n");
    sb.append("Id\tName\t\t\t|Type\t\t|Description\t\t|Team Size\t\t|Budget\n");
    sb.append("--------------------------------------------------------------------------------\n");
    for (Project project : projects) {
      sb.append(project.displayProject());
    }
    sb.append("\n");
    return sb.toString();
  }

  private String listProjects() {
    return listProjects(projects);
  }

  private String listSoftwareProjects() {
    return listProjects(projects.stream().filter(project -> project instanceof SoftwareProject).toList());
  }

  private String listHardwareProjects() {
    return listProjects(projects.stream().filter(project -> project instanceof HardwareProject).toList());
  }

  private String searchByBudgetRange() {
    System.out.print("Enter Minimum Budget: ");
    double min = ConsoleMenu.scanner.nextDouble();
    System.out.print("Enter Maximum Budget: ");
    double max = ConsoleMenu.scanner.nextDouble();
    return listProjects(
        projects.stream().filter(project -> project.getBudget() >= min && project.getBudget() <= max).toList());
  }

  @Override
  public void displayMenu() {
    System.out.printf(
        "1. View  All Projects (%s)\n2. Software Projects Only\n3. Hardware Projects Only\n4. Search by Budget Range\n\n",
        projects.size());

  }

  @Override
  public int handleChoice(int choice) {
    switch (choice) {
      case 1:
        System.out.println(listProjects());
        break;
      case 2:
        System.out.println(listSoftwareProjects());
        break;
      case 3:
        System.out.println(listHardwareProjects());
        break;
      case 4:
        System.out.println(searchByBudgetRange());
        break;
      default:
        return choice;
    }
    return -1;
  }

}
