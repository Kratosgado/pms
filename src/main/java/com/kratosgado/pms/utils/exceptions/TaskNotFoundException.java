
package com.kratosgado.pms.utils.exceptions;

public class TaskNotFoundException extends Exception {

  public TaskNotFoundException() {
    super("Task does not exist");
  }

  public TaskNotFoundException(String message) {
    super(message);
  }
}
