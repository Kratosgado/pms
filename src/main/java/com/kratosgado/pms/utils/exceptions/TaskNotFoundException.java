
package com.kratosgado.pms.utils.exceptions;

public class TaskNotFoundException extends PMSException {

  public TaskNotFoundException() {
    super("Task does not exist");
  }

  public TaskNotFoundException(String message) {
    super(message);
  }
}
