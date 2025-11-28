
package services;

import java.util.ArrayList;

import models.Project;
import utils.ConsoleMenu;

public class ReportService {

  public static void displayReport(ArrayList<Project> projects) {
    ConsoleMenu.displayHeader("REPORT STATUS REPORT");
    StringBuilder sb = new StringBuilder();
    double progressSum = 0;

    ConsoleMenu.appendTableHeader(sb, String.format("%-20s|%-20s|%-20s|%-20s|%-20s", "PROJECT ID", "PROJECT NAME",
        "TASKS", "COMPLETED", "PROGRESS (%)"));
    for (Project project : projects) {
      sb.append(String.format("%-20s", project.getId()));

      sb.append(String.format("|%-20s", project.getName()));
      sb.append(String.format("|%-20s", project.getTasks().size()));
      sb.append(String.format("|%-20s", project.getCompletedTasks()));
      double progress = project.getProgress();
      progressSum += progress;
      sb.append(String.format("|%-20s\n", progress));
    }
    ConsoleMenu.appendTableHeader(sb, "AVERAGE COMPLETION: " + (progressSum / projects.size()) + "%");
    System.out.println(sb.toString());
  }

}
