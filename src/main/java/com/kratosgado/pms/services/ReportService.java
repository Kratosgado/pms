
package com.kratosgado.pms.services;

import com.kratosgado.pms.data.ProjectInMemoryDatabase;
import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.utils.CustomUtils;

public class ReportService {
  private final ProjectInMemoryDatabase projectsDb;

  public ReportService(ProjectInMemoryDatabase projectsDb) {
    this.projectsDb = projectsDb;
  }

  public double calculateAverageCompletionPercentage(final Project[] projects) {
    double progressSum = 0;
    for (final Project project : projects) {
      final double progress = project.calculateCompletionPercentage();
      progressSum += progress;
    }
    return (int) (progressSum / projects.length);
  }

  public void displayReport() {
    CustomUtils.displayHeader("REPORT STATUS REPORT");
    final StringBuilder sb = new StringBuilder();
    double progressSum = 0;

    CustomUtils.appendTableHeader(sb, String.format("%-20s|%-20s|%-20s|%-20s|%-20s", "PROJECT ID", "PROJECT NAME",
        "TASKS", "COMPLETED", "PROGRESS (%)"));
    for (final Project project : projectsDb.getAll()) {
      sb.append(String.format("%-20s", project.getId()));

      sb.append(String.format("|%-20s", project.getName()));
      sb.append(String.format("|%-20s", project.getTasks().length));
      sb.append(String.format("|%-20s", project.getCompletedTasks()));
      final double progress = project.calculateCompletionPercentage();
      progressSum += progress;
      sb.append(String.format("|%-20.2f\n", progress));
    }
    CustomUtils.appendTableHeader(sb,
        "AVERAGE COMPLETION: " + String.format("%.2f%%", (progressSum / projectsDb.count())));
    System.out.println(sb.toString());
  }

}
