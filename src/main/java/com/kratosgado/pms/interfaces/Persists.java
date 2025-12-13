
package com.kratosgado.pms.interfaces;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public interface Persists {

  /**
   * Saves the data to the file
   * 
   * @throws IOException
   */
  public void saveData() throws IOException;

  /**
   * Checks if the data exists in the file
   * 
   * @return true if the data exists, false otherwise
   */
  public boolean fileExists();

  /**
   * Loads the data from the file
   * 
   * @throws IOException
   */
  public void loadData() throws IOException;

  /**
   * Reads the file and returns the stripped content of the file as a string
   * 
   * @param fileName
   * @return the content of the file as a string
   * @throws IOException
   */
  default public String readFile(String fileName) throws IOException {

    return Files.readString(Path.of(fileName)).replaceAll("\s+([\"\\{\\}\\[\\]])", "$1");
  }
}
