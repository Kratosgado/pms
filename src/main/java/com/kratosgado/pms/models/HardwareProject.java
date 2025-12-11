package com.kratosgado.pms.models;

import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.enums.ProjectType;

public class HardwareProject extends Project {

  public HardwareProject(String id, String name, String description, int teamSize, double budget) {
    super(id, name, description, teamSize, budget);
  }

  @Override
  public String getProjectDetails() {

    StringBuilder sb = new StringBuilder();
    sb.append("\tName: ").append(getName()).append("\n");
    sb.append("\tDescription: ").append(getDescription()).append("\n");
    sb.append("\tType: ").append(getProjectType()).append("\n");
    sb.append("\tTeam Size: ").append(getTeamSize()).append("\n");
    sb.append("\tBudget: ").append(getBudget()).append("\n");

    sb.append("\tAssociated Tasks: ").append(getTasks().size()).append("\n");

    CustomUtils.appendTableHeader(sb, String.format("%-20s|%-20s|%-20s|%-20s", "ID", "NAME", "STATUS", "HOURS"));
    for (Task task : getTasks()) {
      sb.append(task.toString());
    }

    sb.append(String.format("Completion Rate: %.2f%%\n", calculateCompletionPercentage()));
    return sb.toString();
  }

  @Override
  public ProjectType getProjectType() {
    return ProjectType.HARDWARE;
  }

  public static Project fromJson(String json) {
    int idStart = json.indexOf("\"id\":\"") + 6;
    int idEnd = json.indexOf("\",", idStart + 1);
    String id = json.substring(idStart, idEnd);

    int nameStart = json.indexOf("\"name\":\"") + 8;
    int nameEnd = json.indexOf("\",", nameStart + 1);
    String name = json.substring(nameStart, nameEnd);

    int descriptionStart = json.indexOf("\"description\":\"") + 15;
    int descriptionEnd = json.indexOf("\",", descriptionStart + 1);
    String description = json.substring(descriptionStart, descriptionEnd);

    int teamSizeStart = json.indexOf("\"teamSize\":") + 11;
    int teamSizeEnd = json.indexOf(",", teamSizeStart + 1);
    String teamSize = json.substring(teamSizeStart, teamSizeEnd);

    int budgetStart = json.indexOf("\"budget\":") + 10;
    int budgetEnd = json.indexOf("\",", budgetStart);
    String budget = json.substring(budgetStart, budgetEnd);

    int tasksStart = json.indexOf("\"tasks\":") + 8;
    int tasksEnd = json.indexOf("}", tasksStart + 1);
    String tasks = json.substring(tasksStart, tasksEnd);

    Project project = new SoftwareProject(id, name, description, Integer.parseInt(teamSize),
        Double.parseDouble(budget));
    // project.setTasks(tasks); TODO: fix tasks operations
    return project;
  }
}
