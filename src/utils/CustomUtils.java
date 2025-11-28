
package utils;

public class CustomUtils {
  public static final int UI_MAX_WIDTH = 100;
  public static final int UI_MAX_CELL_WIDTH = 20;

  public final static String getNextId(String prefix, int length) {
    return prefix + String.format("%03d", length);
  }
}
