
package utils;

public class CustomUtils {
  private final static String alphaNumeric = "abcdefghijklmnopqrstuvwxyz1234567890";
  private final static int LENGTH = alphaNumeric.length();

  public final static String generateID() {
    StringBuilder id = new StringBuilder();

    for (int i = 0; i < 6; i++) {
      id.append(getRandomChar());
    }
    id.append('-');
    for (int i = 0; i < 4; i++) {
      id.append(getRandomChar());
    }
    id.append('-');

    for (int i = 0; i < 4; i++) {
      id.append(getRandomChar());
    }
    id.append('-');

    for (int i = 0; i < 8; i++) {
      id.append(getRandomChar());
    }

    return id.toString();
  }

  private final static char getRandomChar() {
    return alphaNumeric.charAt(((int) Math.floor(Math.random() * LENGTH)));

  }

  public final static String getNextId(String prefix, int length) {
    return prefix + String.format("%03d", length);
  }
}
