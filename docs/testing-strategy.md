# Testing Strategy - PMS Task Management Application

## Overview

This document outlines the testing approach for the PMS application as part of Sprint 1 (US-3: Automated Testing Framework).

---

## Testing Philosophy

Our testing strategy focuses on **testing core business logic** rather than UI code. Since PMS is a console-based application with significant UI/presentation code in service classes, we concentrate testing efforts on:

1. **Models** - Business entities and their behavior
2. **Data Layer** - Repository patterns and database operations
3. **Business Logic** - Calculations, validations, and core algorithms
4. **Context Management** - Authentication and authorization

---

## Test Coverage Summary

### Current Status
- **Total Tests:** 103
- **Test Suites:** 11
- **Coverage Focus:** Business logic and data layer

### Package Breakdown

| Package | Tests | Focus Area |
|---------|-------|------------|
| `models` | 51 | Entity behavior, calculations, data integrity |
| `data` | 39 | CRUD operations, persistence, database logic |
| `services` | 6 | Business logic calculations (ReportService) |
| `utils.context` | 12 | Authentication, authorization |

---

## Test Categories

### 1. Unit Tests

**Purpose:** Test individual components in isolation

**Examples:**
- `UserTest.java` - User model behavior (17 tests)
- `TaskTest.java` - Task model behavior (10 tests)
- `ProjectTest.java` - Project model behavior (9 tests)
- `AuthManagerTest.java` - Authentication logic (12 tests)

**Coverage:**
- Getters/setters
- Business logic methods
- State transitions
- Edge cases and boundary conditions

### 2. Integration Tests

**Purpose:** Test component interactions and data flow

**Examples:**
- `RepositoryTest.java` - Data persistence operations (5 tests)
- `UserInMemoryDatabaseTest.java` - Database with repository (18 tests)
- `ProjectInMemoryDatabaseTest.java` - Complex data operations (16 tests)

**Coverage:**
- CRUD operations
- Data validation
- Conflict handling
- Entity relationships

### 3. Business Logic Tests

**Purpose:** Test calculations and reporting

**Examples:**
- `ReportServiceTest.java` - Completion percentage calculations (5 tests)

**Coverage:**
- Progress calculations
- Average computations
- Edge cases (empty projects, 100% complete, 0% complete)

---

## Testing Tools & Frameworks

### JUnit 5
- **Version:** 5.11.0-M2
- **Usage:** Test framework for all test suites
- **Features Used:**
  - `@Test` annotations
  - `@BeforeEach` setup methods
  - Assertions (assertEquals, assertTrue, assertThrows, etc.)

### JaCoCo
- **Version:** 0.8.11
- **Usage:** Code coverage reporting
- **Configuration:**
  - Generates HTML reports in `target/site/jacoco/`
  - Coverage threshold: 70% (for business logic packages)

### Maven Surefire
- **Version:** 3.2.5
- **Usage:** Test execution plugin
- **Features:**
  - Parallel test execution
  - Test result reporting

---

## Running Tests

### Run All Tests
```bash
mvn test
```

### Run Tests with Coverage Report
```bash
mvn clean test
# View report: target/site/jacoco/index.html
```

### Run Specific Test Class
```bash
mvn test -Dtest=UserTest
```

### Run Tests in Specific Package
```bash
mvn test -Dtest="com.kratosgado.pms.models.*Test"
```

---

## Test Naming Convention

We follow the pattern: `methodName_scenario_expectedBehavior()`

**Examples:**
- `testAdd_user_successfully()`
- `testRequireAdmin_throwsExceptionWhenNotLoggedIn()`
- `testAverageCompletionPercentage_multipleProjects()`

---

## What We Test

### ✅ Areas Covered

1. **Model Entities**
   - User (Regular and Admin)
   - Task (status management, completion tracking)
   - Project (Software and Hardware types)
   - Completion calculations

2. **Data Operations**
   - Add, get, update, delete operations
   - ID generation
   - Conflict detection
   - Entity existence checks

3. **Authentication & Authorization**
   - User login state management
   - Admin privilege verification
   - User switching

4. **Business Calculations**
   - Project completion percentages
   - Average progress across projects
   - Task statistics

### ❌ Areas NOT Tested

1. **Console UI Code**
   - User input prompts
   - Menu display logic
   - Console formatting
   - **Rationale:** UI testing for console apps provides limited value; focus on business logic

2. **JSON Parsing**
   - Complex string manipulation for JSON parsing
   - **Rationale:** Fragile code that should be replaced with proper JSON library

3. **File I/O**
   - Actual file read/write operations
   - **Rationale:** Would require complex mocking; database tests cover persistence logic

---

## Test Quality Standards

### All Tests Must:
1. **Be Independent** - Tests don't depend on execution order
2. **Be Repeatable** - Same input produces same result every time
3. **Be Fast** - Individual tests complete in milliseconds
4. **Be Clear** - Test name and assertions make intent obvious
5. **Test One Thing** - Each test focuses on a single behavior

### Good Test Example:
```java
@Test
void testAdd_duplicateId_throwsConflictException() throws ConflictException {
    User user1 = new RegularUser("U001", "User One", "user1@example.com", "pass1");
    User user2 = new RegularUser("U001", "User Two", "user2@example.com", "pass2");

    usersDb.add(user1);

    assertThrows(ConflictException.class, () -> {
        usersDb.add(user2);
    });
}
```

---

## Coverage Goals

### Target Coverage by Package

| Package | Target | Rationale |
|---------|--------|-----------|
| `models` | 70%+ | Core business entities |
| `data` | 70%+ | Critical persistence logic |
| `utils.context` | 70%+ | Security-critical code |
| `services` | 30%+ | Mostly UI code, test business methods only |
| `utils` | 40%+ | Mix of UI and business utilities |

### Why Not 100% Coverage?

1. **Console UI Code** - Testing console I/O is impractical and low-value
2. **Generated Code** - Getters/setters don't need explicit tests
3. **Exception Constructors** - Simple pass-through code
4. **toString/toJson** - Formatting code is tested indirectly

---

## Continuous Improvement

### Sprint 1 Achievements
- ✅ JaCoCo integration
- ✅ 103 comprehensive tests
- ✅ Focus on business logic
- ✅ Automated test execution

### Future Enhancements (Sprint 2+)
- Add Mockito for service mocking
- Integration tests with actual JSON files
- Performance tests for large datasets
- Parameterized tests for edge cases

---

## CI/CD Integration

Tests are integrated into the CI/CD pipeline (US-7):
- **Trigger:** Every push and pull request
- **Failure:** Pipeline fails if any test fails
- **Reports:** Coverage reports generated automatically
- **Quality Gate:** 70% coverage threshold for business logic

---

## Summary

Our testing strategy prioritizes **quality over quantity**, focusing on:
- **Business-critical code** (models, data layer, calculations)
- **Fast, reliable tests** that provide confidence
- **Maintainable test suite** that evolves with the codebase

This approach ensures we have **confidence in our core logic** while avoiding the trap of testing low-value code just to hit arbitrary coverage numbers.

---

**Last Updated:** 2026-02-11
**Sprint:** Sprint 1
**User Story:** US-3 - Automated Testing Framework
