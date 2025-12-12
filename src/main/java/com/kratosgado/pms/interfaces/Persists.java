
package com.kratosgado.pms.interfaces;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public interface Persists {

  public void saveData() throws IOException;

  public boolean dataExists();

  public void loadData() throws IOException;

  default public String readFile(String fileName) throws IOException {

    return Files.readString(Path.of(fileName)).replaceAll("\s+([\"\\{\\}\\[\\]])", "$1");
  }
}
