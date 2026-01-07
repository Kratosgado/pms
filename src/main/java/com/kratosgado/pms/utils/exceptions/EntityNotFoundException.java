
package com.kratosgado.pms.utils.exceptions;

public class EntityNotFoundException extends Exception {

  public EntityNotFoundException() {
    super("Entity does not exist");
  }

  public EntityNotFoundException(String message) {
    super(message);
  }

}
