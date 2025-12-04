
package com.kratosgado.pms.services;

import com.kratosgado.pms.utils.CustomUtils;

public abstract class ConsoleService {
  protected String title;

  public final void displayMenu() {
    CustomUtils.displayHeader(title);
    displayOptions();
    System.out.println("9. Exit");
    System.out.println("0. Go Back");
  }

  protected abstract void displayOptions();

  public abstract int handleChoice(int choice);

}
