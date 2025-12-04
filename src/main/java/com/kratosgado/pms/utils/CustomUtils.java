package com.kratosgado.pms.utils;

public class CustomUtils {
  public static final int UI_MAX_WIDTH = 100;
  public static final int UI_MAX_CELL_WIDTH = 20;

  public static final int DEFAULT_MEMORY_CAPACITY = 50;

  public final static String getNextId(final String prefix, final int length) {
    return prefix + String.format("%03d", length);
  }

  /**
   * Appends a table header to a string builder
   * 
   * @param stringBuilder the string builder to append the table header to
   * @param title         the title of the table
   */
  public final static void appendTableHeader(final StringBuilder stringBuilder, final String title) {
    stringBuilder.append("||").append("-".repeat(CustomUtils.UI_MAX_WIDTH)).append("||").append("\n");
    stringBuilder.append(title).append("\n");
    stringBuilder.append("||").append("-".repeat(CustomUtils.UI_MAX_WIDTH)).append("||").append("\n");
  }

  /**
   * Displays a header to the user
   * 
   * @param title the title of the header
   */
  public final static void displayHeader(final String title) {
    final int halfLength = title.length() % 2 == 0 ? title.length() / 2 : title.length() / 2 + 1;
    System.out.println("\n" + "=".repeat(CustomUtils.UI_MAX_WIDTH));
    System.out.printf("|| %s %s %s||\n", " ".repeat(45 - halfLength), title,
        " ".repeat(48 - halfLength));
    System.out.println("=".repeat(CustomUtils.UI_MAX_WIDTH));
  }

  /**
   * Displays an error message to the user
   * 
   * @param error the error message to display to the user
   */
  public final static void displayError(String name, final String error) {
    System.out.println("\n❌ " + name + ": " + error);
  }

  /**
   * Displays a success message to the user
   * 
   * @param success the success message to display to the user
   */
  public final static void displaySuccess(final String success) {
    System.out.println("\n✅ " + success);
  }
}
