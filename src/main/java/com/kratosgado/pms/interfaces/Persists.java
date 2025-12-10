
package com.kratosgado.pms.interfaces;

public interface Persists {

  public void saveData(String fileName);

  public boolean dataExists(String fileName);

  public void loadData(String fileName);
}
