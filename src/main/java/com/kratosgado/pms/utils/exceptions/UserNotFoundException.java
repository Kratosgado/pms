
package com.kratosgado.pms.utils.exceptions;

public class UserNotFoundException extends PMSException {

  public UserNotFoundException() {
    super("User does not exist");
  }

  public UserNotFoundException(String message) {
    super(message);
  }
}
