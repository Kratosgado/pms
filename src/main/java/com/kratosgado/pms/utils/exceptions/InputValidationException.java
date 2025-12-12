
package com.kratosgado.pms.utils.exceptions;

public class InputValidationException extends RuntimeException {

  public InputValidationException() {
    super("Invalid Input. Check and Enter a valid input");
  }

  public InputValidationException(String message) {
    super(message);
  }

}
