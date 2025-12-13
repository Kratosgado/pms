
package com.kratosgado.pms.services;

import com.kratosgado.pms.utils.CustomUtils;

public abstract class ConsoleService {
  protected String title;

  /**
   * Displays the menu to the user.<br>
   * The menu is displayed in the following order:<br>
   * 1. Displays the title of the service<br>
   * 2. Displays the options of the service<br>
   */
  public final void displayMenu() {
    CustomUtils.displayHeader(title);
    displayOptions();
    System.out.println("9. Exit");
    System.out.println("0. Go Back");
    System.out.println("100. Save Data");
    System.out.println("101. Load Data");
  }

  /**
   * Displays the options available in the service.
   */
  protected abstract void displayOptions();

  /**
   * Handles the choice of the user.
   *
   * @param choice the choice of the user
   * @return -1 if choice was handled by the service, otherwise the choice
   */
  public abstract int handleChoice(int choice);

}
