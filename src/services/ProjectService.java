
package services;

import java.util.ArrayList;
import java.util.HashMap;

import models.Project;

public class ProjectService extends MainService {
  private ArrayList<Project> projects;

  public ProjectService(ArrayList<Project> projects) {
    this.projects = projects;
  }

  public String listProjects() {
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
        System.out.println("Software Projects Only");
        break;
      case 3:
        System.out.println("Hardware Projects Only");
        break;
      case 4:
        System.out.println("Search by Budget Range");
        break;
      default:
        System.out.println("Invalid Choice");
    }
    return -1;
  }

}
