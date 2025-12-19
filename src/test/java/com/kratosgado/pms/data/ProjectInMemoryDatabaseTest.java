package com.kratosgado.pms.data;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.models.SoftwareProject;
import com.kratosgado.pms.models.HardwareProject;
import com.kratosgado.pms.utils.enums.ProjectType;
import com.kratosgado.pms.utils.exceptions.ConflictException;
import com.kratosgado.pms.utils.exceptions.EntityNotFoundException;

public class ProjectInMemoryDatabaseTest {

  private ProjectInMemoryDatabase projectDb;

  @BeforeEach
  void setUp() {
    projectDb = new ProjectInMemoryDatabase();
  }

  @Test
  void testProjectFileExists() throws IOException {
    final String fileName = "data/projects.json";
    projectDb = new ProjectInMemoryDatabase(fileName);
    projectDb.saveData();
    assertTrue(projectDb.fileExists());
  }

  @Test
  void testProjectFileDoesNotExist() {
    final String fileName = "nonexistent.json";
    projectDb = new ProjectInMemoryDatabase(fileName);
    assertFalse(projectDb.fileExists());
  }

  @Test
  void testSavingAndLoadingProjects() throws IOException, ConflictException {
    final String fileName = "projects_json.json";
    projectDb = new ProjectInMemoryDatabase(fileName);
    assertThrows(IOException.class, () -> {
      projectDb.loadData();
    });
    projectDb.add("Project 1", "Description 1", 5, 50000.0, ProjectType.SOFTWARE);
    projectDb.saveData();
    assertEquals(1, projectDb.count());
    assertTrue(projectDb.fileExists());
    Files.delete(Path.of(fileName));
  }

  @Test
  void testCreateSoftwareProject() throws ConflictException {
    Project project = projectDb.add("E-commerce Platform", "An online shopping platform", 8, 100000.0,
        ProjectType.SOFTWARE);

    assertNotNull(project);
    assertEquals("E-commerce Platform", project.getName());
    assertEquals("An online shopping platform", project.getDescription());
    assertEquals(8, project.getTeamSize());
    assertEquals(100000.0, project.getBudget());
    assertEquals(ProjectType.SOFTWARE, project.getProjectType());
    assertTrue(project instanceof SoftwareProject);
  }

  @Test
  void testCreateHardwareProject() throws ConflictException {
    Project project = projectDb.add("IoT Sensor", "Smart sensor device", 4, 50000.0, ProjectType.HARDWARE);

    assertNotNull(project);
    assertEquals("IoT Sensor", project.getName());
    assertEquals("Smart sensor device", project.getDescription());
    assertEquals(4, project.getTeamSize());
    assertEquals(50000.0, project.getBudget());
    assertEquals(ProjectType.HARDWARE, project.getProjectType());
    assertTrue(project instanceof HardwareProject);
  }

  /**
   * @throws ConflictException
   * 
   */
  @Test
  void testProjectIdGeneration() throws ConflictException {
    Project project1 = projectDb.add("Project 1", "Description 1", 5, 50000.0, ProjectType.SOFTWARE);
    Project project2 = projectDb.add("Project 2", "Description 2", 3, 30000.0, ProjectType.HARDWARE);

    assertNotNull(project1.getId());
    assertNotNull(project2.getId());
    assertNotEquals(project1.getId(), project2.getId());
    assertTrue(project1.getId().startsWith("PJ"));
    assertTrue(project2.getId().startsWith("PJ"));
  }

  @Test
  void testAddMultipleProjects() throws ConflictException {
    projectDb.add("Project A", "Description A", 5, 50000.0, ProjectType.SOFTWARE);
    projectDb.add("Project B", "Description B", 3, 30000.0, ProjectType.HARDWARE);
    projectDb.add("Project C", "Description C", 7, 75000.0, ProjectType.SOFTWARE);

    assertEquals(3, projectDb.count());
  }

  @Test
  void testGetProjectById() throws EntityNotFoundException, ConflictException {
    Project created = projectDb.add("Test Project", "Test Description", 5, 50000.0, ProjectType.SOFTWARE);
    Project retrieved = projectDb.getById(created.getId());

    assertNotNull(retrieved);
    assertEquals(created.getName(), retrieved.getName());
    assertEquals(created.getId(), retrieved.getId());
  }

  @Test
  void testRemoveProject() throws EntityNotFoundException, ConflictException {
    Project project = projectDb.add("Project to Remove", "Will be removed", 5, 50000.0, ProjectType.SOFTWARE);
    String id = project.getId();

    assertEquals(1, projectDb.count());
    projectDb.removeById(id);
    assertEquals(0, projectDb.count());
  }

  @Test
  void testMultipleProjectTypes() throws ConflictException {
    Project swProject = projectDb.add("Web App", "Description 1", 5, 50000.0, ProjectType.SOFTWARE);
    Project hwProject = projectDb.add("PCB Board", "Description 2", 3, 30000.0, ProjectType.HARDWARE);

    assertEquals(ProjectType.SOFTWARE, swProject.getProjectType());
    assertEquals(ProjectType.HARDWARE, hwProject.getProjectType());
  }

  @Test
  void testProjectBudgetStorage() throws ConflictException {
    Project lowBudget = projectDb.add("Low Budget", "Description 1", 2, 10000.0, ProjectType.SOFTWARE);
    Project mediumBudget = projectDb.add("Medium Budget", "Description 2", 5, 50000.0, ProjectType.SOFTWARE);
    Project highBudget = projectDb.add("High Budget", "Description 3", 10, 100000.0, ProjectType.HARDWARE);

    assertEquals(10000.0, lowBudget.getBudget());
    assertEquals(50000.0, mediumBudget.getBudget());
    assertEquals(100000.0, highBudget.getBudget());
  }

  @Test
  void testProjectTeamSizeStorage() throws ConflictException {
    Project smallTeam = projectDb.add("Small Team", "Description 1", 2, 10000.0, ProjectType.SOFTWARE);
    Project mediumTeam = projectDb.add("Medium Team", "Description 2", 5, 50000.0, ProjectType.SOFTWARE);
    Project largeTeam = projectDb.add("Large Team", "Description 3", 10, 100000.0, ProjectType.HARDWARE);

    assertEquals(2, smallTeam.getTeamSize());
    assertEquals(5, mediumTeam.getTeamSize());
    assertEquals(10, largeTeam.getTeamSize());
  }

  @Test
  void testUpdateProject() throws ConflictException, EntityNotFoundException {
    Project project = projectDb.add("Original Name", "Original Description", 5, 50000.0, ProjectType.SOFTWARE);
    project.setName("Updated Name");
    project.setDescription("Updated Description");
    project.setBudget(75000.0);

    Project updated = projectDb.update(project);
    assertNotNull(updated);
    assertEquals("Updated Name", updated.getName());
    assertEquals("Updated Description", updated.getDescription());
    assertEquals(75000.0, updated.getBudget());
  }

  @Test
  void testProjectExists() throws ConflictException {
    Project project = projectDb.add("Existing Project", "Description", 5, 50000.0, ProjectType.SOFTWARE);

    assertTrue(projectDb.exists(project.getId()));
    assertFalse(projectDb.exists("NONEXISTENT_ID"));
  }

  @Test
  void testAddDirectlyProject() throws ConflictException {
    SoftwareProject project = new SoftwareProject("PJ999", "Direct Add", "Added directly", 3, 30000.0);
    projectDb.add(project);

    assertEquals(1, projectDb.count());
    assertTrue(projectDb.exists("PJ999"));
  }

  @Test
  void testCountProjects() throws ConflictException {
    assertEquals(0, projectDb.count());
    projectDb.add("Project 1", "Description 1", 5, 50000.0, ProjectType.SOFTWARE);
    assertEquals(1, projectDb.count());
    projectDb.add("Project 2", "Description 2", 3, 30000.0, ProjectType.HARDWARE);
    assertEquals(2, projectDb.count());
    projectDb.add("Project 3", "Description 3", 7, 70000.0, ProjectType.SOFTWARE);
    assertEquals(3, projectDb.count());
  }

}
