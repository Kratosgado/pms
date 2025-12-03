package com.kratosgado.pms.models;

public class HardwareProject extends Project {

  public HardwareProject(String id, String name, String description, int teamSize, double budget) {
    super(id, name, description, teamSize, budget);
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
