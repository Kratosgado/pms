
package com.kratosgado.pms.utils.exceptions;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException() {
    super("User does not exist");
  }

  public UserNotFoundException(String message) {
    super(message);
  }
}
