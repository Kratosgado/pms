package com.kratosgado.pms.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kratosgado.pms.utils.enums.ProjectType;

public class SoftwareProjectTest {
  private SoftwareProject project;

  @BeforeEach
  void setUp() {
    project = new SoftwareProject("SP001", "E-commerce Platform", "Online shopping system", 10, 50000.0);
  }

  @Test
  void testSoftwareProjectCreation() {
    assertNotNull(project);
    assertEquals("SP001", project.getId());
    assertEquals("E-commerce Platform", project.getName());
    assertEquals("Online shopping system", project.getDescription());
    assertEquals(10, project.getTeamSize());
    assertEquals(50000.0, project.getBudget());
  }

  @Test
  void testGetProjectType_returnsSoftware() {
    assertEquals(ProjectType.SOFTWARE, project.getProjectType());
  }

  @Test
  void testToString_containsSoftwareType() {
    String result = project.toString();
    assertTrue(result.contains("SOFTWARE"));
    assertTrue(result.contains("SP001"));
    assertTrue(result.contains("E-commerce Platform"));
  }

  @Test
  void testToJson_containsSoftwareType() {
    String json = project.toJson();
    assertTrue(json.contains("\"projectType\":\"SOFTWARE\""));
    assertTrue(json.contains("\"id\":\"SP001\""));
    assertTrue(json.contains("\"name\":\"E-commerce Platform\""));
  }

  @Test
  void testGetProjectDetails_containsKeyInformation() {
    String details = project.getProjectDetails();
    assertNotNull(details);
    assertTrue(details.contains("E-commerce Platform"));
    assertTrue(details.contains("Online shopping system"));
    assertTrue(details.contains("SOFTWARE"));
  }
}
