
package models;

public class HardwareProject extends Project {

  public HardwareProject(String id, String name, String description, int teamSize, double budget) {
    super(id, name, description, teamSize, budget);
  }

  @Override
  String getProjectDetails() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getProjectDetails'");
  }

}
