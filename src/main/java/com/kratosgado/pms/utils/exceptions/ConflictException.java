
package com.kratosgado.pms.utils.exceptions;

public class ConflictException extends Exception {

  public ConflictException() {
    super("Entity already exists");
  }

  public ConflictException(String message) {
    super(message);
  }
}
