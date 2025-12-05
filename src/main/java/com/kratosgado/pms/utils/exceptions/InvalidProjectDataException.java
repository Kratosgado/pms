
package com.kratosgado.pms.utils.exceptions;

public class InvalidProjectDataException extends PMSException {

  public InvalidProjectDataException() {
    super("Poject has invalid data");
  }

  public InvalidProjectDataException(String message) {
    super(message);
  }

}
