# Product Backlog - PMS Task Management Application

## Product Vision
**"PMS empowers development teams to efficiently manage projects and tasks through an intuitive system that provides real-time progress tracking, role-based access control, and actionable insights—enabling teams to deliver quality software on time."**

---

## Backlog Items

### US-2: Enhanced Task Filtering and Search
- **Priority:** High
- **Story Points:** 5
- **Status:** Completed
- **Sprint:** Sprint 2
- **Assignee:** TBD

**Description:**
As a project manager, I want to filter and search tasks by multiple criteria, so that I can quickly find specific tasks and identify bottlenecks.

**Acceptance Criteria:**
- [ ] Filter tasks by status (Pending, In Progress, Completed)
- [ ] Filter tasks by project
- [ ] Search tasks by name or description (partial match)
- [ ] Combine multiple filters
- [ ] Display filter results with task count summary

---

### US-3: Automated Testing Framework ⭐ Sprint 1
- **Priority:** High
- **Story Points:** 5
- **Status:** Completed
- **Sprint:** Sprint 1
- **Assignee:** Development Team

**Description:**
As a developer, I want comprehensive automated tests for core business logic, so that I can confidently refactor code and prevent regressions.

**Acceptance Criteria:**
- [ ] Unit tests for all service classes (ProjectService, TaskService, UserService, ReportService)
- [ ] Unit tests for critical model classes (Project, Task, User)
- [ ] Integration tests for database operations (Repository, InMemoryDatabase)
- [ ] Test coverage at least 70% for business logic
- [ ] All tests pass with `mvn test`

---

### US-4: Task Assignment and Ownership
- **Priority:** Medium
- **Story Points:** 3
- **Status:** Completed
- **Sprint:** Sprint 2
- **Assignee:** Development Team

**Description:**
As a project manager, I want to assign tasks to specific team members, so that everyone knows their responsibilities.

**Acceptance Criteria:**
- [ ] Assign tasks to specific user during creation
- [ ] Reassign tasks to different users
- [ ] Users can view tasks assigned to them
- [ ] Task display shows assigned user's name
- [ ] Only admins and assigned user can update task status

---

### US-5: Comprehensive Logging System ⭐ Sprint 1
- **Priority:** Medium
- **Story Points:** 3
- **Status:** Completed
- **Sprint:** Sprint 1
- **Assignee:** Development Team

**Description:**
As a system administrator, I want detailed application logs for key operations, so that I can troubleshoot issues and audit user actions.

**Acceptance Criteria:**
- [ ] Log all authentication attempts (success/failure)
- [ ] Log all CRUD operations with user and timestamp
- [ ] Log all errors with stack traces
- [ ] Include severity levels (INFO, WARN, ERROR)
- [ ] Write logs to console and file (logs/app.log)

---

### US-6: Task Priority Levels
- **Priority:** Medium
- **Story Points:** 2
- **Status:** Not Started
- **Sprint:** Sprint 2
- **Assignee:** TBD

**Description:**
As a team member, I want to set priority levels for tasks, so that I can focus on the most important work first.

**Acceptance Criteria:**
- [ ] Support priority levels: Critical, High, Medium, Low
- [ ] Set priority during task creation
- [ ] Update priority after creation
- [ ] Sort/filter by priority level
- [ ] Display priority in task listings

---

### US-7: CI/CD Pipeline Setup ⭐ Sprint 1
- **Priority:** High
- **Story Points:** 5
- **Status:** Completed
- **Sprint:** Sprint 1
- **Assignee:** Development Team

**Description:**
As a developer, I want an automated CI/CD pipeline, so that code changes are automatically tested and validated.

**Acceptance Criteria:**
- [ ] GitHub Actions workflow file exists and configured
- [ ] Pipeline runs on every push and pull request
- [ ] Pipeline stages: compile, test, code quality check
- [ ] Pipeline fails if tests fail or code doesn't compile
- [ ] Pipeline status badge in README

---

### US-8: System Diagnostics Command
- **Priority:** Low
- **Story Points:** 2
- **Status:** Not Started
- **Sprint:** Future
- **Assignee:** TBD

**Description:**
As a DevOps engineer, I want a system diagnostics command, so that I can monitor if the application is running correctly.

**Acceptance Criteria:**
- [ ] 'status' command prints system information (uptime, memory, DB status)
- [ ] Command executes within 500ms
- [ ] Output is formatted as a readable table
- [ ] Documented in user guide

---

## Sprint Planning

### Sprint 1 (⭐ marked items)
**Goal:** Establish DevOps foundation with automated testing and CI/CD pipeline

| Story | Points | Priority |
|-------|--------|----------|
| US-3: Automated Testing Framework | 5 | High |
| US-7: CI/CD Pipeline Setup | 5 | High |
| US-5: Comprehensive Logging System | 3 | Medium |
| **Total** | **13** | |

### Sprint 2 (Planned)
**Goal:** Deliver core feature enhancements for better task management

| Story | Points | Priority |
|-------|--------|----------|
| US-2: Enhanced Filtering | 5 | High |
| US-4: Task Assignment | 3 | Medium |
| US-6: Task Priority | 2 | Medium |
| **Total** | **18** | |

### Backlog (Future Sprints)
| Story | Points | Priority |
|-------|--------|----------|
| US-8: System Diagnostics Command | 2 | Low |

---

## Backlog Metrics
- **Total Stories:** 8
- **Total Story Points:** 25
- **Sprint 1 Points:** 13
- **Sprint 2 Planned Points:** 10
- **Average Story Size:** 4.1 points

---

**Last Updated:** 2026-02-11
