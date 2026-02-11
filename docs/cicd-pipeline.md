# CI/CD Pipeline Documentation - PMS Application

## Overview

This document describes the Continuous Integration and Continuous Deployment (CI/CD) pipeline for the PMS Task Management Application, implemented as part of Sprint 1 (US-6: CI/CD Pipeline Setup).

---

## Pipeline Overview

The CI/CD pipeline is implemented using **GitHub Actions** and automatically runs on every code push and pull request to ensure code quality and prevent regressions.

### Workflow File
- **Location:** `.github/workflows/maven.yml`
- **Name:** Java CI with Maven
- **Platform:** Ubuntu Latest

---

## Trigger Conditions

The pipeline automatically runs when:

### 1. Push Events
```yaml
branches: [ "main", "module-*", "feature/*" ]
```
- Push to `main` branch
- Push to any `module-*` branch (e.g., `module-1`, `module-2`)
- Push to any `feature/*` branch (e.g., `feature/us-3-automated-testing`)

### 2. Pull Request Events
```yaml
branches: [ "main" ]
```
- Pull requests targeting the `main` branch
- Ensures code review changes are validated before merge

---

## Pipeline Stages

The pipeline consists of 9 stages that run sequentially:

1. **Checkout code** - Clone repository
2. **Set up JDK 21** - Install Java with Maven caching
3. **Build with Maven** - Compile source code
4. **Run tests** - Execute all 103 tests
5. **Generate coverage report** - Create JaCoCo report
6. **Upload to Codecov** (optional) - External coverage tracking
7. **Archive test results** - Save test reports
8. **Archive coverage report** - Save coverage HTML
9. **Check test results** - Ensure pipeline fails on errors

---

## Pipeline Success Criteria

The pipeline is considered **successful** when:

✅ All source code compiles without errors
✅ All 103 tests pass successfully
✅ Coverage report generates successfully
✅ No critical build errors occur

The pipeline **fails** when:

❌ Compilation errors exist
❌ Any test fails
❌ Build process errors

---

## Viewing Pipeline Results

### Status Badge

The README displays a real-time status badge showing pipeline status.

### On GitHub

1. Navigate to repository **Actions** tab
2. Select workflow run from list
3. View job details and logs
4. Download artifacts (test results, coverage reports)

---

## Pipeline Performance

### Typical Run Times

| Stage | Duration |
|-------|----------|
| Checkout | ~5s |
| JDK Setup | ~10-30s (cached) |
| Build | ~10-20s |
| Tests | ~5-10s |
| Coverage | ~2-5s |
| Artifacts | ~5-10s |
| **Total** | **~40-80s** |

---

## Local Pipeline Simulation

Replicate pipeline locally:

```bash
# Build
mvn clean compile

# Run tests
mvn test

# Generate coverage
mvn jacoco:report

# View coverage
open target/site/jacoco/index.html
```

---

## Integration with Development Workflow

### Feature Development Process

1. Create feature branch
2. Develop and commit changes
3. Push to remote (pipeline runs automatically)
4. Review pipeline results
5. Create pull request (pipeline runs again)
6. Pipeline must pass before merge

### Pull Request Checks

When creating PR to `main`:
- Pipeline must pass
- All tests must succeed
- Code must compile

---

## Troubleshooting

### Pipeline Fails on Build
**Solution:** Run `mvn clean compile` locally to see errors

### Pipeline Fails on Tests
**Solution:** Run `mvn test` locally to debug failing tests

### Pipeline Slow
**Solution:** Wait for Maven cache to populate (first run only)

---

## Future Enhancements (Sprint 2+)

1. **Code Quality Checks** - CheckStyle, SpotBugs, PMD
2. **Deployment Stage** - Auto-deploy to staging
3. **Performance Testing** - JMH benchmarks
4. **Security Scanning** - OWASP dependency check
5. **Release Automation** - Versioning and changelogs

---

## Summary

The CI/CD pipeline provides **automated quality assurance** for every code change, ensuring the PMS application maintains high quality and reliability throughout development.

---

**Last Updated:** 2026-02-11
**Sprint:** Sprint 1
**User Story:** US-6 - CI/CD Pipeline Setup
**Workflow File:** `.github/workflows/maven.yml`
