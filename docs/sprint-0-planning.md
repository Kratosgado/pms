# Sprint 0: Planning Phase - PMS Task Management Application

## 1. Product Vision

**"PMS empowers development teams to efficiently manage projects and tasks through an intuitive system that provides real-time progress tracking, role-based access control, and actionable insights—enabling teams to deliver quality software on time."**

---

## 2. Product Backlog

### User Story 2: Enhanced Task Filtering and Search
**Priority:** High
**Story Points:** 5

**User Story:**
As a **project manager**, I want **to filter and search tasks by multiple criteria**, so that **I can quickly find specific tasks and identify bottlenecks in my projects**.

**Acceptance Criteria:**
1. Users can filter tasks by status (Pending, In Progress, Completed)
2. Users can filter tasks by project
3. Users can search tasks by name or description (partial match support)
4. Multiple filters can be combined (e.g., show all "In Progress" tasks in "Project A")
5. Filter results display in a readable format with task count summary

---

### User Story 3: Automated Testing Framework
**Priority:** High
**Story Points:** 5

**User Story:**
As a **developer**, I want **comprehensive automated tests for core business logic**, so that **I can confidently refactor code and prevent regressions**.

**Acceptance Criteria:**
1. Unit tests exist for all service classes (ProjectService, TaskService, UserService, ReportService)
2. Unit tests exist for critical model classes (Project, Task, User)
3. Integration tests verify database operations (Repository, InMemoryDatabase)
4. Test coverage is at least 70% for business logic
5. All tests pass successfully with `mvn test` command

---

### User Story 4: Task Assignment and Ownership
**Priority:** Medium
**Story Points:** 3

**User Story:**
As a **project manager**, I want **to assign tasks to specific team members**, so that **everyone knows their responsibilities and I can track individual workload**.

**Acceptance Criteria:**
1. Tasks can be assigned to a specific user during creation
2. Tasks can be reassigned to different users after creation
3. Users can view all tasks assigned to them (filtered view)
4. Task display shows the assigned user's name
5. Only admins and the assigned user can update task status

---

### User Story 5: Comprehensive Logging System
**Priority:** Medium
**Story Points:** 3

**User Story:**
As a **system administrator**, I want **detailed application logs for key operations**, so that **I can troubleshoot issues, audit user actions, and monitor system health**.

**Acceptance Criteria:**
1. Application logs all authentication attempts (success and failure)
2. Application logs all CRUD operations (create, update, delete) with user and timestamp
3. Application logs all errors with stack traces
4. Logs include different severity levels (INFO, WARN, ERROR)
5. Logs are written to both console and file (logs/app.log)

---

### User Story 6: Task Priority Levels
**Priority:** Medium
**Story Points:** 2

**User Story:**
As a **team member**, I want **to set priority levels for tasks**, so that **I can focus on the most important work first**.

**Acceptance Criteria:**
1. Tasks support priority levels: Critical, High, Medium, Low
2. Priority can be set during task creation
3. Priority can be updated after task creation
4. Tasks can be sorted/filtered by priority level
5. Priority is displayed in task listings

---

### User Story 7: CI/CD Pipeline Setup
**Priority:** High
**Story Points:** 5

**User Story:**
As a **developer**, I want **an automated CI/CD pipeline**, so that **code changes are automatically tested and validated before merging**.

**Acceptance Criteria:**
1. GitHub Actions workflow file exists and is properly configured
2. Pipeline runs automatically on every push and pull request
3. Pipeline includes stages: compile, test, code quality check
4. Pipeline fails if tests fail or code doesn't compile
5. Pipeline status badge is displayed in README

---

### User Story 8: System Diagnostics Command
**Priority:** Low
**Story Points:** 2

**User Story:**
As a **DevOps engineer**, I want **a system diagnostics command**, so that **I can monitor if the application is running correctly**.

**Acceptance Criteria:**
1. 'status' command prints system information (uptime, memory usage, database connection status)
2. Command executes within 500ms
3. Output is formatted as a readable table
4. Command does not require authentication
5. Command is documented in user guide

---

## 3. Definition of Done (DoD)

A user story is considered "Done" when ALL of the following criteria are met:

### Code Quality Standards
- [ ] Code follows Java naming conventions and style guidelines
- [ ] No compiler warnings or errors
- [ ] Code is properly formatted and indented
- [ ] No hardcoded values (use configuration files or constants)
- [ ] Exception handling is implemented appropriately
- [ ] Code smells identified by linting tools are resolved

### Testing Requirements
- [ ] Unit tests written for new/modified business logic
- [ ] All new tests pass successfully
- [ ] Existing tests continue to pass (no regressions)
- [ ] Test coverage for new code is at least 70%
- [ ] Edge cases and error scenarios are tested

### Documentation Updates
- [ ] Code includes Javadoc comments for public methods
- [ ] README updated with new features or setup changes
- [ ] JavaDoc updated (if applicable)
- [ ] Inline comments explain complex logic

### Code Review Completion
- [ ] Code reviewed by at least one team member
- [ ] All review comments addressed or discussed
- [ ] PR approved by reviewer(s)

### CI/CD Pipeline Success
- [ ] All automated tests pass in CI pipeline
- [ ] Build completes successfully
- [ ] No linting or code quality check failures

### Deployment Readiness
- [ ] Feature merged to main branch
- [ ] No known critical bugs
- [ ] Feature is demonstrable to stakeholders
- [ ] Acceptance criteria verified and met

---

## 4. Sprint 1 Planning

### Sprint 1 Goal
Establish foundational DevOps infrastructure and deliver initial high-priority features to enable automated testing and core capabilities.

### Selected User Stories for Sprint 1

#### Story 1: US-3 - Automated Testing Framework
**Priority:** High | **Story Points:** 5
**Rationale:** Essential foundation for DevOps practices. Required before CI/CD implementation.

#### Story 2: US-7 - CI/CD Pipeline Setup
**Priority:** High | **Story Points:** 5
**Rationale:** Core DevOps requirement. Depends on having tests to run in the pipeline.

#### Story 3: US-5 - Comprehensive Logging System
**Priority:** Medium | **Story Points:** 3
**Rationale:** Provides visibility into system behavior and is relatively self-contained.

**Total Story Points:** 13

### Sprint 1 Capacity Planning
- Sprint duration: 1-2 weeks
- Focus: DevOps infrastructure (60%) + Feature delivery (40%)
- Risk: Setting up CI/CD for the first time may take longer than estimated
- Mitigation: If time is limited, US-5 (Logging) can be moved to Sprint 2

### Sprint 1 Tasks Breakdown

#### US-3: Automated Testing Framework
1. Set up JUnit 5 testing dependencies (already present)
2. Write unit tests for ProjectService methods
3. Write unit tests for TaskService methods
4. Write unit tests for UserService methods
5. Write integration tests for Repository classes
6. Configure test coverage reporting (JaCoCo plugin)
7. Verify 70% coverage threshold

#### US-7: CI/CD Pipeline Setup
1. Create `.github/workflows/maven.yml` file
2. Configure pipeline stages: compile, test, package
3. Add code quality checks (optional: CheckStyle or SpotBugs)
4. Test pipeline with sample commits
5. Add pipeline status badge to README
6. Document pipeline in repository

#### US-5: Comprehensive Logging System
1. Add logging framework dependency (SLF4J + Logback)
2. Create logging configuration file
3. Add logging to authentication operations
4. Add logging to CRUD operations in services
5. Add error logging with stack traces
6. Test log output to console and file

### Sprint 1 Dependencies
- US-7 (CI/CD) depends on US-3 (Tests) being substantially complete
- US-5 (Logging) is independent and can be developed in parallel

### Sprint 1 Exit Criteria
- All selected user stories meet Definition of Done
- CI/CD pipeline successfully runs on every commit
- Test suite executes automatically in pipeline
- Sprint Review document completed with screenshots
- Sprint Retrospective conducted and documented

---

## Backlog Prioritization Summary

| Story | Title | Priority | Points | Sprint |
|-------|-------|----------|--------|--------|
| US-3 | Automated Testing Framework | High | 5 | Sprint 1 |
| US-7 | CI/CD Pipeline Setup | High | 5 | Sprint 1 |
| US-5 | Comprehensive Logging System | Medium | 3 | Sprint 1 |
| US-2 | Enhanced Task Filtering | High | 5 | Sprint 2 |
| US-4 | Task Assignment and Ownership | Medium | 3 | Sprint 2 |
| US-6 | Task Priority Levels | Medium | 2 | Sprint 2 |
| US-8 | System Diagnostics Command | Low | 2 | Future |

**Total Backlog:** 33 Story Points

---

## Notes and Assumptions

1. **Team Velocity:** Assuming 12-15 story points per sprint based on Sprint 1 planning
2. **Technical Debt:** Current system has good structure; no major refactoring needed
3. **Dependencies:** US-2 (Filtering) and US-4 (Assignment) are high priority for Sprint 2 to enhance user experience
4. **Tools:** Using GitHub Actions for CI/CD (free for public repos)
5. **Testing:** JUnit 5 already in pom.xml; need to add test coverage reporting

---

**Document Version:** 1.0
**Created:** 2026-02-11
**Sprint Start Date:** 2026-01-07
**Sprint End Date:** 2026-01-14
