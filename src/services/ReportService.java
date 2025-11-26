
package services;

import java.util.ArrayList;

import models.Project;
import utils.ConsoleMenu;

public class ReportService {

  public static void displayReport(ArrayList<Project> projects) {
    ConsoleMenu.displayHeader("REPORT STATUS REPORT");
    StringBuilder sb = new StringBuilder();
    double progressSum = 0;

    sb.append("\n------------------------------------------------------------------------------------\n");
    sb.append("PROJECT ID | PROJECT NAME | TASKS | COMPLETED | PROGRESS (%)\n");
    sb.append("------------------------------------------------------------------------------------\n");
    for (Project project : projects) {
      sb.append(project.getId());
      sb.append(" | ");
      sb.append(project.getName());
      sb.append(" | ");
      sb.append(project.getTasks().size());
      sb.append(" | ");
      sb.append(project.getCompletedTasks());
      sb.append(" | ");
      double progress = project.getProgress();
      progressSum += progress;
      sb.append(progress);
      sb.append("\n");
    }
    sb.append("\n------------------------------------------------------------------------------------\n");
    sb.append("AVERAGE COMPLETION: ")
        .append(progressSum / projects.size()).append("%\n");
    sb.append("\n------------------------------------------------------------------------------------\n");
    System.out.println(sb.toString());
  }

}
