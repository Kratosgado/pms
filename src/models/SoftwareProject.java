
package models;

public class SoftwareProject extends Project {

  public SoftwareProject(String name, String description, int teamSize, double budget) {
    super(name, description, teamSize, budget);
  }

  @Override
  String getProjectDetails() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getProjectDetails'");
  }

}
