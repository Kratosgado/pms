
package com.kratosgado.pms.utils.exceptions;

public class PMSException extends Exception {

  public PMSException() {
  }

  public PMSException(String message) {
    super(message);
  }

  public PMSException(String message, Throwable cause) {
    super(message, cause);
  }

  public PMSException(Throwable cause) {
    super(cause);
  }
}
