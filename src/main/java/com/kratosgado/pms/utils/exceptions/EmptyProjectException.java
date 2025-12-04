
package com.kratosgado.pms.utils.exceptions;

public class EmptyProjectException extends PMSException {
  public EmptyProjectException() {
    super("Project cannot be empty");
  }

}
