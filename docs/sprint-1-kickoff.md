# Sprint 1 Kickoff - PMS Task Management Application

## Sprint Overview

**Sprint Number:** 1
**Sprint Duration:** 1-2 weeks
**Sprint Start Date:** 2026-01-14
**Sprint End Date:** 2026-01-28
**Sprint Goal:** Establish foundational DevOps infrastructure and deliver initial high-priority features to enable automated testing and continuous integration.

---

## Sprint 1 Backlog

### Selected User Stories (Total: 13 Story Points)

#### 1. US-3: Automated Testing Framework (5 points) - HIGH PRIORITY

**Status:** Not Started
**Assignee:** TBD
**Dependencies:** None

**Goal:** Implement comprehensive unit and integration tests for core business logic to enable confident refactoring and prevent regressions.

**Tasks:**

- [ ] Configure JUnit 5 and test dependencies (already present)
- [ ] Add JaCoCo Maven plugin for test coverage reporting
- [ ] Write unit tests for ProjectService (create, update, delete, get)
- [ ] Write unit tests for TaskService (create, update, delete, get)
- [ ] Write unit tests for UserService (authentication, CRUD operations)
- [ ] Write unit tests for ReportService (statistics, progress calculation)
- [ ] Write integration tests for Repository classes
- [ ] Write integration tests for InMemoryDatabase classes
- [ ] Verify test coverage reaches 70% threshold
- [ ] Document testing strategy in README

**Acceptance Criteria Checklist:**

- [ ] Unit tests exist for all service classes
- [ ] Unit tests exist for critical model classes
- [ ] Integration tests verify database operations
- [ ] Test coverage is at least 70%
- [ ] All tests pass with `mvn test` command

---

#### 2. US-7: CI/CD Pipeline Setup (5 points) - HIGH PRIORITY

**Status:** Not Started
**Assignee:** TBD
**Dependencies:** US-3 (Tests must exist to run in pipeline)

**Goal:** Implement automated CI/CD pipeline using GitHub Actions to automatically test and validate code changes.

**Tasks:**

- [ ] Create `.github/workflows/maven.yml` configuration file
- [ ] Configure pipeline to trigger on push and pull_request events
- [ ] Add job: Checkout code
- [ ] Add job: Set up JDK 21
- [ ] Add job: Build with Maven (`mvn compile`)
- [ ] Add job: Run tests (`mvn test`)
- [ ] Add job: Generate test coverage report
- [ ] (Optional) Add code quality checks (CheckStyle or SpotBugs)
- [ ] Test pipeline with sample commits
- [ ] Add pipeline status badge to README.md
- [ ] Document pipeline stages and purpose

**Acceptance Criteria Checklist:**

- [ ] GitHub Actions workflow file exists and is configured
- [ ] Pipeline runs automatically on every push and PR
- [ ] Pipeline includes stages: compile, test, code quality
- [ ] Pipeline fails if tests fail or code doesn't compile
- [ ] Pipeline status badge displayed in README

---

#### 3. US-5: Comprehensive Logging System (3 points) - MEDIUM PRIORITY

**Status:** Not Started
**Assignee:** TBD
**Dependencies:** None (Can be developed in parallel)

**Goal:** Implement detailed application logging for key operations to enable troubleshooting, auditing, and system health monitoring.

**Tasks:**

- [ ] Add SLF4J and Logback dependencies to pom.xml
- [ ] Create `src/main/resources/logback.xml` configuration file
- [ ] Configure log levels and output formats
- [ ] Configure file appender (logs/app.log) with rotation
- [ ] Add logging to AuthManager (authentication success/failure)
- [ ] Add logging to ProjectService CRUD operations
- [ ] Add logging to TaskService CRUD operations
- [ ] Add logging to UserService CRUD operations
- [ ] Add error logging with stack traces in exception handlers
- [ ] Create logs directory and add to .gitignore
- [ ] Test log output to both console and file
- [ ] Document logging configuration in README

**Acceptance Criteria Checklist:**

- [ ] Application logs all authentication attempts
- [ ] Application logs all CRUD operations with user and timestamp
- [ ] Application logs all errors with stack traces
- [ ] Logs include different severity levels (INFO, WARN, ERROR)
- [ ] Logs written to both console and file (logs/app.log)

---

## Sprint 1 Execution Strategy

### Week 1 Focus (if 2-week sprint)

1. **Day 1-2:** Set up testing framework (US-3)
   - Configure JaCoCo
   - Write unit tests for service classes
   - Aim for 50% coverage by end of day 2

2. **Day 3-4:** Complete testing and start logging (US-3 + US-5)
   - Write integration tests
   - Reach 70% coverage threshold
   - Add logging dependencies and configuration

3. **Day 5:** Set up CI/CD pipeline (US-7)
   - Create GitHub Actions workflow
   - Configure basic pipeline stages
   - Test initial pipeline run

### Week 2 Focus (if 2-week sprint)

1. **Day 1-2:** Complete logging implementation (US-5)
   - Add logging to all service classes
   - Test log output
   - Verify log rotation

2. **Day 3-4:** Enhance and finalize CI/CD (US-7)
   - Add code quality checks
   - Optimize pipeline performance
   - Add status badge
   - Document pipeline

3. **Day 5:** Testing, documentation, and Sprint Review prep
   - Verify all DoD criteria met
   - Update README and documentation
   - Prepare demo screenshots
   - Write Sprint Review document

---

## Daily Standup Questions

Each day, team members should be prepared to answer:

1. What did I complete yesterday?
2. What will I work on today?
3. Are there any blockers or impediments?

---

## Risk Assessment

### Identified Risks

| Risk                                    | Probability | Impact | Mitigation Strategy                               |
| --------------------------------------- | ----------- | ------ | ------------------------------------------------- |
| CI/CD setup takes longer than estimated | Medium      | Medium | Start with basic pipeline, enhance incrementally  |
| Test coverage difficult to reach 70%    | Low         | Medium | Focus on service layer first, models secondary    |
| Logging framework integration issues    | Low         | Low    | Use well-documented SLF4J/Logback stack           |
| Time constraint with 3 stories          | Medium      | High   | US-5 (Logging) can be moved to Sprint 2 if needed |

---

## Sprint Ceremonies Schedule

### Sprint Planning (Completed)

- Backlog reviewed and stories selected
- Tasks identified and estimated
- Team capacity confirmed

### Daily Standup

- **Time:** TBD (suggest: 9:00 AM daily)
- **Duration:** 15 minutes max
- **Format:** Sync or async (team choice)

### Sprint Review

- **When:** Last day of sprint
- **Duration:** 1 hour
- **Attendees:** Development team + stakeholders
- **Agenda:**
  - Demo completed features
  - Show CI/CD pipeline in action
  - Review acceptance criteria fulfillment
  - Collect feedback

### Sprint Retrospective

- **When:** After Sprint Review
- **Duration:** 45 minutes
- **Attendees:** Development team only
- **Focus:**
  - What went well?
  - What could be improved?
  - Action items for Sprint 2

---

## Definition of Done Reference

Before marking any story as "Done", verify:

- [ ] All acceptance criteria met
- [ ] Code reviewed and approved
- [ ] All tests passing in CI pipeline
- [ ] Code coverage threshold met
- [ ] Documentation updated
- [ ] No critical bugs or blockers

See `/docs/definition-of-done.md` for complete checklist.

---

## Git Workflow for Sprint 1

### Branch Naming Convention

- Feature branches: `feature/us-3-automated-testing`
- Format: `feature/us-[number]-[short-description]`

### Commit Message Format

Use conventional commits:

- `feat: add unit tests for ProjectService`
- `test: add integration tests for Repository`
- `ci: create GitHub Actions workflow`
- `docs: update README with testing instructions`
- `fix: resolve test failure in TaskService`

### Pull Request Process

1. Create feature branch from `main`
2. Make small, frequent commits (aim for 8-10+ commits per story)
3. Open PR when story is complete
4. Request code review
5. Address review comments
6. Merge only after CI pipeline passes and PR is approved

---

## Tools and Resources

### Required Tools

- **IDE:** IntelliJ IDEA, Eclipse, or VS Code
- **Build Tool:** Maven 3.8.6+
- **Version Control:** Git
- **CI/CD:** GitHub Actions (already available)
- **Test Coverage:** JaCoCo (to be added)
- **Logging:** SLF4J + Logback (to be added)

### Documentation References

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [JaCoCo Maven Plugin](https://www.jacoco.org/jacoco/trunk/doc/maven.html)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [SLF4J Manual](http://www.slf4j.org/manual.html)
- [Logback Documentation](http://logback.qos.ch/documentation.html)

---

## Sprint Success Metrics

### Quantitative Metrics

- [ ] 13 story points completed
- [ ] Test coverage ≥ 70%
- [ ] CI pipeline success rate ≥ 95%
- [ ] 0 critical bugs introduced
- [ ] 8-10+ meaningful commits per story

### Qualitative Metrics

- [ ] Team confidence in automated testing
- [ ] Positive feedback on CI/CD setup
- [ ] Improved development workflow
- [ ] Clear logging visibility

---

## Next Steps to Begin Sprint 1

1. **Set Sprint Dates:**
   - Confirm sprint start and end dates
   - Schedule Sprint Review and Retrospective

2. **Create Feature Branches:**

   ```bash
   git checkout -b feature/us-3-automated-testing
   git checkout main
   git checkout -b feature/us-7-cicd-pipeline
   git checkout main
   git checkout -b feature/us-5-logging-system
   ```

3. **Assign Stories:**
   - Assign each story to team member(s)
   - Update backlog with assignments

4. **Begin Development:**
   - Start with US-3 (Testing) as it's a dependency
   - Make first commit to kick off the sprint

5. **Set Up Daily Standup:**
   - Agree on time and format
   - Create Slack channel or meeting invite

---

## Questions or Concerns?

Before starting the sprint, ensure:

- [ ] All team members understand the sprint goal
- [ ] All user stories are clear and unambiguous
- [ ] Definition of Done is understood and agreed upon
- [ ] Development environment is set up
- [ ] Access to required tools and resources confirmed

---

**Sprint Status:** Ready to Begin
**Created:** 2026-02-11
**Last Updated:** 2026-02-11

---

**Let's build something great! 🚀**
