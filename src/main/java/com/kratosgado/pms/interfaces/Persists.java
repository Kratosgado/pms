
package com.kratosgado.pms.interfaces;

import java.io.IOException;

public interface Persists {

  public void saveData() throws IOException;

  public boolean dataExists();

  public void loadData() throws IOException;
}
