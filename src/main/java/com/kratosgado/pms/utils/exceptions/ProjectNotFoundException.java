
package com.kratosgado.pms.utils.exceptions;

public class ProjectNotFoundException extends PMSException {

  public ProjectNotFoundException() {
    super("Project does not exist");
  }

  public ProjectNotFoundException(String message) {
    super(message);
  }
}
