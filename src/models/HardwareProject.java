
package models;

public class HardwareProject extends Project {

  public HardwareProject(String id, String name, int teamSize, double budget) {
    super(id, name, teamSize, budget);
  }

  @Override
  public String getProjectDetails() {
    return displayProject();
  }

  @Override
  public String getProjectType() {
    return "Hardware";
  }

}
