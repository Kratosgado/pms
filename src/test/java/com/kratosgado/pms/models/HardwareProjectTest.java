package com.kratosgado.pms.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kratosgado.pms.utils.enums.ProjectType;

public class HardwareProjectTest {
  private HardwareProject project;

  @BeforeEach
  void setUp() {
    project = new HardwareProject("HP001", "Smart Home Hub", "IoT device for home automation", 8, 75000.0);
  }

  @Test
  void testHardwareProjectCreation() {
    assertNotNull(project);
    assertEquals("HP001", project.getId());
    assertEquals("Smart Home Hub", project.getName());
    assertEquals("IoT device for home automation", project.getDescription());
    assertEquals(8, project.getTeamSize());
    assertEquals(75000.0, project.getBudget());
  }

  @Test
  void testGetProjectType_returnsHardware() {
    assertEquals(ProjectType.HARDWARE, project.getProjectType());
  }

  @Test
  void testToString_containsHardwareType() {
    String result = project.toString();
    assertTrue(result.contains("HARDWARE"));
    assertTrue(result.contains("HP001"));
    assertTrue(result.contains("Smart Home Hub"));
  }

  @Test
  void testToJson_containsHardwareType() {
    String json = project.toJson();
    assertTrue(json.contains("\"projectType\":\"HARDWARE\""));
    assertTrue(json.contains("\"id\":\"HP001\""));
    assertTrue(json.contains("\"name\":\"Smart Home Hub\""));
  }

  @Test
  void testGetProjectDetails_containsKeyInformation() {
    String details = project.getProjectDetails();
    assertNotNull(details);
    assertTrue(details.contains("Smart Home Hub"));
    assertTrue(details.contains("IoT device for home automation"));
    assertTrue(details.contains("HARDWARE"));
  }
}
