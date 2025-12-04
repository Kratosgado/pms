
package com.kratosgado.pms.services;

import java.util.ArrayList;

import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.utils.CustomUtils;

public class ReportService {

  public int calculateAverageCompletionPercentage(final ArrayList<Project> projects) {
    double progressSum = 0;
    for (final Project project : projects) {
      final double progress = project.calculateCompletionPercentage();
      progressSum += progress;
    }
    return (int) (progressSum / projects.size());
  }

  public static void displayReport(final ArrayList<Project> projects) {
    CustomUtils.displayHeader("REPORT STATUS REPORT");
    final StringBuilder sb = new StringBuilder();
    double progressSum = 0;

    CustomUtils.appendTableHeader(sb, String.format("%-20s|%-20s|%-20s|%-20s|%-20s", "PROJECT ID", "PROJECT NAME",
        "TASKS", "COMPLETED", "PROGRESS (%)"));
    for (final Project project : projects) {
      sb.append(String.format("%-20s", project.getId()));

      sb.append(String.format("|%-20s", project.getName()));
      sb.append(String.format("|%-20s", project.getTasks().size()));
      sb.append(String.format("|%-20s", project.getCompletedTasks()));
      final double progress = project.calculateCompletionPercentage();
      progressSum += progress;
      sb.append(String.format("|%-20.2f\n", progress));
    }
    CustomUtils.appendTableHeader(sb,
        "AVERAGE COMPLETION: " + String.format("%.2f%%", (progressSum / projects.size())));
    System.out.println(sb.toString());
  }

}
