
package models;

public class SoftwareProject extends Project {

  public SoftwareProject(String id, String name, String description, int teamSize, double budget) {
    super(id, name, description, teamSize, budget);
  }

  @Override
  String getProjectDetails() {
    throw new UnsupportedOperationException("Unimplemented method 'getProjectDetails'");
  }

}
