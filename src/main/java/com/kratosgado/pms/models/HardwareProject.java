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
      sb.append("\n");
    }

    sb.append(String.format("Completion Rate: %.2f%%\n", calculateCompletionPercentage()));
    return sb.toString();
  }

  @Override
  public ProjectType getProjectType() {
    return ProjectType.HARDWARE;
  }

}
