package com.kratosgado.pms.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.models.SoftwareProject;
import com.kratosgado.pms.utils.enums.ProjectType;
import com.kratosgado.pms.utils.exceptions.ConflictException;
import com.kratosgado.pms.utils.exceptions.EntityNotFoundException;

public class ProjectInMemoryDatabaseTest {

  private ProjectInMemoryDatabase projectDb;

  @TempDir
  Path tempDir;

  @BeforeEach
  void setUp() {
    projectDb = new ProjectInMemoryDatabase();
  }

  @Test
  void testSaveDataCreatesFile() throws IOException, ConflictException {

    Path file = tempDir.resolve("projects.json");
    projectDb = new ProjectInMemoryDatabase(file.toString());
    projectDb.add("Project 1", "Description 1", 5, 50000.0, ProjectType.SOFTWARE);

    projectDb.saveData();

    assertTrue(Files.exists(file), "The file should be created after saving data.");
  }

  @Test
  void testLoadDataThrowsExceptionForNonexistentFile() {

    Path file = tempDir.resolve("nonexistent.json");
    projectDb = new ProjectInMemoryDatabase(file.toString());

    assertThrows(IOException.class, () -> projectDb.loadData(),
        "Loading data from a nonexistent file should throw an IOException.");
  }

  @Test
  void testSaveAndLoadData() throws IOException, ConflictException, EntityNotFoundException {

    Path file = tempDir.resolve("projects.json");
    projectDb = new ProjectInMemoryDatabase(file.toString());
    Project originalProject = projectDb.add("Project 1", "Description 1", 5, 50000.0, ProjectType.SOFTWARE);
    projectDb.saveData();

    ProjectInMemoryDatabase newDb = new ProjectInMemoryDatabase(file.toString());
    newDb.loadData();

    assertEquals(1, newDb.count(), "The new database should have one project after loading.");
    Project loadedProject = newDb.getById(originalProject.getId());
    assertEquals(originalProject.getName(), loadedProject.getName(),
        "The loaded project name should match the original.");
  }

  @ParameterizedTest
  @EnumSource(ProjectType.class)
  void testCreateProjectTypes(ProjectType projectType) throws ConflictException {
    Project project = projectDb.add("E-commerce Platform", "An online shopping platform", 8, 100000.0,
        projectType);

    assertNotNull(project, "Project should not be null");
    assertEquals("E-commerce Platform", project.getName(), "Project name should match");
    assertEquals("An online shopping platform", project.getDescription(), "Project description should match");
    assertEquals(8, project.getTeamSize(), "Project team size should match");
    assertEquals(100000.0, project.getBudget(), "Project budget should match");
    assertEquals(projectType, project.getProjectType(), "Project type should match");
  }

  @ParameterizedTest
  @CsvSource({
      "Project 1,Description 1,5,50000.0",
      "Project 2,Description 2,3,30000.0",
      "Project 3,Description 3,7,70000.0"
  })
  void testProjectIdGenerationAndCount(String name, String description, int teamSize, double budget) throws ConflictException {
    Project project = projectDb.add(name, description, teamSize, budget, ProjectType.SOFTWARE);

    assertNotNull(project.getId(), "Project ID should not be null");
    assertTrue(project.getId().startsWith("PJ"), "Project ID should start with PJ");
    assertEquals(1, projectDb.count(), "Project count should be 1 after adding");
  }

  @Test
  void testProjectIdUniqueness() throws ConflictException {
    Project project1 = projectDb.add("Project 1", "Description 1", 5, 50000.0, ProjectType.SOFTWARE);
    Project project2 = projectDb.add("Project 2", "Description 2", 3, 30000.0, ProjectType.HARDWARE);
    Project project3 = projectDb.add("Project 3", "Description 3", 7, 75000.0, ProjectType.SOFTWARE);

    assertNotEquals(project1.getId(), project2.getId(), "Project IDs should be unique");
    assertNotEquals(project2.getId(), project3.getId(), "Project IDs should be unique");
    assertNotEquals(project1.getId(), project3.getId(), "Project IDs should be unique");
    assertEquals(3, projectDb.count(), "Should have 3 projects in the database");
  }

  @Test
  void testGetProjectById() throws EntityNotFoundException, ConflictException {
    Project created = projectDb.add("Test Project", "Test Description", 5, 50000.0, ProjectType.SOFTWARE);
    Project retrieved = projectDb.getById(created.getId());

    assertNotNull(retrieved, "Retrieved project should not be null");
    assertEquals(created.getName(), retrieved.getName(), "Retrieved project name should match created project name");
    assertEquals(created.getId(), retrieved.getId(), "Retrieved project ID should match created project ID");
  }

  @Test
  void testRemoveProject() throws EntityNotFoundException, ConflictException {
    Project project = projectDb.add("Project to Remove", "Will be removed", 5, 50000.0, ProjectType.SOFTWARE);
    String id = project.getId();

    assertEquals(1, projectDb.count(), "Project count should be 1 before removal");
    projectDb.removeById(id);
    assertEquals(0, projectDb.count(), "Project count should be 0 after removal");
  }

  @Test
  void testMultipleProjectTypes() throws ConflictException {
    Project swProject = projectDb.add("Web App", "Description 1", 5, 50000.0, ProjectType.SOFTWARE);
    Project hwProject = projectDb.add("PCB Board", "Description 2", 3, 30000.0, ProjectType.HARDWARE);

    assertEquals(ProjectType.SOFTWARE, swProject.getProjectType(), "Project type should be SOFTWARE");
    assertEquals(ProjectType.HARDWARE, hwProject.getProjectType(), "Project type should be HARDWARE");
  }

  @Test
  void testUpdateProject() throws ConflictException, EntityNotFoundException {
    Project project = projectDb.add("Original Name", "Original Description", 5, 50000.0, ProjectType.SOFTWARE);
    project.setName("Updated Name");
    project.setDescription("Updated Description");
    project.setBudget(75000.0);

    projectDb.update(project);
    Project updated = projectDb.getById(project.getId());

    assertNotNull(updated, "Updated project should not be null");
    assertEquals("Updated Name", updated.getName(), "Name should be updated");
    assertEquals("Updated Description", updated.getDescription(), "Description should be updated");
    assertEquals(75000.0, updated.getBudget(), "Budget should be updated");
  }

  @Test
  void testProjectExists() throws ConflictException {
    Project project = projectDb.add("Existing Project", "Description", 5, 50000.0, ProjectType.SOFTWARE);

    assertTrue(projectDb.exists(project.getId()), "exists() should return true for existing project");
    assertFalse(projectDb.exists("NONEXISTENT_ID"), "exists() should return false for non-existent project");
  }

  @Test
  void testAddDirectlyProject() throws ConflictException {
    SoftwareProject project = new SoftwareProject("PJ999", "Direct Add", "Added directly", 3, 30000.0);
    projectDb.add(project);

    assertEquals(1, projectDb.count(), "Count should be 1 after adding project directly");
    assertTrue(projectDb.exists("PJ999"), "Project added directly should exist");
  }

  @Test
  void testCountProjects() throws ConflictException {
    assertEquals(0, projectDb.count(), "Initially, project count should be 0");
    projectDb.add("Project 1", "Description 1", 5, 50000.0, ProjectType.SOFTWARE);
    assertEquals(1, projectDb.count(), "Project count should be 1 after adding one project");
    projectDb.add("Project 2", "Description 2", 3, 30000.0, ProjectType.HARDWARE);
    assertEquals(2, projectDb.count(), "Project count should be 2 after adding two projects");
    projectDb.add("Project 3", "Description 3", 7, 70000.0, ProjectType.SOFTWARE);
    assertEquals(3, projectDb.count(), "Project count should be 3 after adding three projects");
  }

}
