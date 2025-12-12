
package com.kratosgado.pms.utils.exceptions;

public class EmptyProjectException extends RuntimeException {
  public EmptyProjectException() {
    super("Project cannot be empty");
  }

}
