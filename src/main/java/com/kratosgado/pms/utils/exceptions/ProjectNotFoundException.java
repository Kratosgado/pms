
package com.kratosgado.pms.utils.exceptions;

public class ProjectNotFoundException extends EntityNotFoundException {

  public ProjectNotFoundException() {
    super("Project does not exist");
  }

  public ProjectNotFoundException(String message) {
    super(message);
  }
}
