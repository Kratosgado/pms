
package models;

public class SoftwareProject extends Project {

  public SoftwareProject(String id, String name, int teamSize, double budget) {
    super(id, name, teamSize, budget);
  }

  @Override
  public String getProjectDetails() {
    return displayProject();
  }

  @Override
  public String getProjectType() {
    return "Software";
  }

}
