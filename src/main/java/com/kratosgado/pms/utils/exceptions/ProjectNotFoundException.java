
package com.kratosgado.pms.utils.exceptions;

public class ProjectNotFoundException extends RuntimeException {

  public ProjectNotFoundException() {
    super("Task not found");
  }

  public ProjectNotFoundException(String message) {
    super(message);
  }
}
