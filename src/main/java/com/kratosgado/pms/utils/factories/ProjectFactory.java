package com.kratosgado.pms.utils.factories;

import com.kratosgado.pms.models.HardwareProject;
import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.models.SoftwareProject;
import com.kratosgado.pms.utils.enums.ProjectType;

public class ProjectFactory {
  public Project createProject(String id, String name, String description,
      int teamSize, double budget, ProjectType type) {
    switch (type) {
      case SOFTWARE:
        return new SoftwareProject(id, name, description, teamSize, budget);
      case HARDWARE:
        return new HardwareProject(id, name, description, teamSize, budget);
      default:
        throw new IllegalArgumentException("Unknown project type: " + type);
    }
  }
}
