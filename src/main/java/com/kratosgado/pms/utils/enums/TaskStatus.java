
package com.kratosgado.pms.utils.enums;

public enum TaskStatus {
  PENDING("Pending"),
  IN_PROGRESS("In Progress"),
  COMPLETED("Completed");

  private String status;

  TaskStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

}
