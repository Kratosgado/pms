# Project Manager System

## Feature 1: Project Catalog Management

- Create new projects (eg. SoftwareProject, HardwareProject)
- View all existing projects with details (ID, name, description, team size, budget, etc)
- Filter projects by type (Software/Hardware)
- Display project-specific attributes dynamically.

## Feature 2: Task Operations

- Add tasks to specific projects
- Assign task status (Pending, In Progress, Completed)
- View all tasks per project with progress details
- Update or delete tasks
- validate inputs to prevent invalid task status or duplicate task names

## Feature 3; User Management

- Create and manage users (RegularUser and ADminUser)
- Assign users to projects
- Enforce role-based access (Admin can delete/update; Regular can view/add)
- Automatically generate unique user IDs

## Feature 4: Status Processing & Reporting

- Calculate and display completion averages per project
- Generate status reports (e.g, "Poject Alpha is 75% complete")
- Display task statistics total, completed, and pending counts
- Show per-user performance summaries (future expansion)

### Feature 5: Menu Navigation & User Experience

- Display a clear main menu and sub-menus for operations
- Validate all user inputs (numbers, text, Ids)
- Provide formatted outputs with clear sections and alignment
- Support graceful exit and return navigation
