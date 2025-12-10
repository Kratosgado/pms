
package com.kratosgado.pms.data.dto;

public record ReportDto(ProjectDetailDto[] projectDetails) {

  public double getCompletionPercentage() {
    double sum = 0.0;
    for (ProjectDetailDto projectDetailDto : projectDetails) {
      sum += projectDetailDto.progress();
    }
    return sum / projectDetails.length;
  }
}
