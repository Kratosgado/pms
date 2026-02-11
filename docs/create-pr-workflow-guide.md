# Create PR Workflow Guide

This guide explains how to use the `create-pr.yml` GitHub Actions workflow to automatically create pull requests from n8n or manually.

---

## Overview

The workflow can create pull requests in two ways:
1. **Via n8n** (using repository_dispatch)
2. **Manually** (using workflow_dispatch from GitHub UI)

---

## Method 1: Triggering from n8n

### Setup

1. **Get GitHub Personal Access Token (PAT)**
   - Go to GitHub Settings → Developer settings → Personal access tokens → Tokens (classic)
   - Generate new token with scopes: `repo`, `workflow`
   - Save the token securely

2. **Configure n8n Workflow**

Add an **HTTP Request** node with these settings:

**Authentication:**
- Authentication: Generic Credential Type
- Generic Auth Type: Header Auth
- Name: `Authorization`
- Value: `Bearer YOUR_GITHUB_TOKEN`

**Request Settings:**
- Method: `POST`
- URL: `https://api.github.com/repos/OWNER/REPO/dispatches`
  - Replace `OWNER` with your GitHub username (e.g., `Kratosgado`)
  - Replace `REPO` with your repository name (e.g., `pms`)
- Send Headers: Yes
  - Header: `Accept`
  - Value: `application/vnd.github.v3+json`

**Body (JSON):**
```json
{
  "event_type": "create-pr",
  "client_payload": {
    "title": "{{ $json.pr_title }}",
    "body": "{{ $json.pr_description }}",
    "source_branch": "{{ $json.source_branch }}",
    "target_branch": "{{ $json.target_branch }}",
    "draft": false
  }
}
```

### Example n8n Payload

**Minimal (required fields only):**
```json
{
  "event_type": "create-pr",
  "client_payload": {
    "title": "Add new feature",
    "body": "This PR adds a new feature to the application."
  }
}
```

**Complete (with all options):**
```json
{
  "event_type": "create-pr",
  "client_payload": {
    "title": "Sprint 3: New Features",
    "body": "## Summary\nThis PR includes:\n- Feature A\n- Feature B\n\n## Testing\nAll tests passing",
    "source_branch": "feature/sprint-3",
    "target_branch": "main",
    "draft": false
  }
}
```

### Parameters

| Parameter | Required | Default | Description |
|-----------|----------|---------|-------------|
| `title` | ✅ Yes | - | PR title (e.g., "Add login feature") |
| `body` | ✅ Yes | - | PR description (supports markdown) |
| `source_branch` | ❌ No | `develop` | Branch to merge from (head) |
| `target_branch` | ❌ No | `main` | Branch to merge into (base) |
| `draft` | ❌ No | `false` | Create as draft PR |

---

## Method 2: Manual Trigger (GitHub UI)

### Steps

1. Go to your repository on GitHub
2. Click **Actions** tab
3. Select **Create Pull Request from n8n** workflow
4. Click **Run workflow** dropdown
5. Fill in the form:
   - **Pull Request Title**: Your PR title
   - **Pull Request Body/Description**: Your PR description (markdown supported)
   - **Source branch**: Branch to merge from (e.g., `feature/new-feature`)
   - **Target branch**: Branch to merge into (e.g., `main`)
   - **Create as draft PR**: Check if you want a draft
6. Click **Run workflow**

---

## Workflow Features

### 1. Branch Validation
- ✅ Checks if source and target branches exist
- ✅ Prevents creating PR if branches are the same
- ✅ Provides clear error messages

### 2. Duplicate Prevention
- ✅ Checks if PR already exists for the same branches
- ✅ Adds comment to existing PR instead of failing
- ✅ Prevents duplicate PRs

### 3. Flexible Triggering
- ✅ Works with n8n automation
- ✅ Works with manual GitHub UI
- ✅ Supports both use cases with same workflow

### 4. Draft Support
- ✅ Can create draft PRs
- ✅ Draft PRs won't trigger status checks
- ✅ Useful for work-in-progress

---

## n8n Workflow Examples

### Example 1: Simple PR Creation
```
Trigger (Webhook/Schedule)
    ↓
Set PR Data
    ↓
HTTP Request (Create PR)
    ↓
Success Notification
```

### Example 2: PR from Git Commits
```
GitHub Trigger (Push to branch)
    ↓
Get Commit Messages
    ↓
Generate PR Description
    ↓
HTTP Request (Create PR)
    ↓
Slack Notification
```

### Example 3: Scheduled Release PR
```
Schedule Trigger (Weekly)
    ↓
Get Version from package.json
    ↓
Set PR Title (e.g., "Release v1.2.3")
    ↓
Generate Changelog
    ↓
HTTP Request (Create PR)
    ↓
Send Email to Team
```

---

## API Endpoint Details

### GitHub API Endpoint
```
POST https://api.github.com/repos/{owner}/{repo}/dispatches
```

### Headers
```
Authorization: Bearer YOUR_GITHUB_TOKEN
Accept: application/vnd.github.v3+json
Content-Type: application/json
```

### Request Body
```json
{
  "event_type": "create-pr",
  "client_payload": {
    "title": "PR Title",
    "body": "PR Description",
    "source_branch": "feature-branch",
    "target_branch": "main",
    "draft": false
  }
}
```

### Response
```
Status: 204 No Content
```

---

## Common Use Cases

### Use Case 1: Automated Release PRs
Create a PR automatically when a release branch is ready:

**n8n Setup:**
- Trigger: GitHub webhook on branch creation
- Condition: Branch name starts with `release/`
- Action: Create PR to `main`

**Example Payload:**
```json
{
  "event_type": "create-pr",
  "client_payload": {
    "title": "Release v{{ $json.version }}",
    "body": "## Release Notes\n\n{{ $json.changelog }}",
    "source_branch": "{{ $json.branch_name }}",
    "target_branch": "main",
    "draft": false
  }
}
```

### Use Case 2: Feature Branch PRs
Automatically create PRs when feature work is done:

**n8n Setup:**
- Trigger: Manual button click in n8n
- Action: Create draft PR for review

**Example Payload:**
```json
{
  "event_type": "create-pr",
  "client_payload": {
    "title": "Feature: {{ $json.feature_name }}",
    "body": "## Feature Description\n{{ $json.description }}\n\n## Checklist\n- [ ] Tests added\n- [ ] Documentation updated",
    "source_branch": "feature/{{ $json.feature_name }}",
    "target_branch": "develop",
    "draft": true
  }
}
```

### Use Case 3: Hotfix PRs
Quick PRs for urgent fixes:

**n8n Setup:**
- Trigger: PagerDuty incident or monitoring alert
- Action: Create PR with incident details

**Example Payload:**
```json
{
  "event_type": "create-pr",
  "client_payload": {
    "title": "Hotfix: {{ $json.incident_title }}",
    "body": "## Incident\nID: {{ $json.incident_id }}\nSeverity: {{ $json.severity }}\n\n## Fix\n{{ $json.fix_description }}",
    "source_branch": "hotfix/{{ $json.incident_id }}",
    "target_branch": "main",
    "draft": false
  }
}
```

---

## Troubleshooting

### Error: "Source branch does not exist"
- **Cause:** The branch you're trying to merge from doesn't exist
- **Solution:**
  1. Check the branch name spelling
  2. Ensure the branch exists: `git branch -r | grep branch-name`
  3. Push the branch: `git push origin branch-name`

### Error: "Target branch does not exist"
- **Cause:** The branch you're trying to merge into doesn't exist
- **Solution:**
  1. Check the target branch name
  2. Common targets: `main`, `develop`, `master`
  3. Verify: `git branch -r | grep main`

### Error: "Source and target branches cannot be the same"
- **Cause:** You specified the same branch for source and target
- **Solution:** Use different branches (e.g., source: `feature/x`, target: `main`)

### PR Already Exists
- **Behavior:** Workflow will add a comment to existing PR
- **Not an Error:** This prevents duplicate PRs
- **Solution:** If you want to create a new PR, close the old one first

### n8n: "Request failed with status 401"
- **Cause:** Invalid or expired GitHub token
- **Solution:**
  1. Verify token has `repo` and `workflow` scopes
  2. Check token hasn't expired
  3. Generate new token if needed

### n8n: "Request failed with status 404"
- **Cause:** Repository not found or incorrect URL
- **Solution:**
  1. Check repository owner/name in URL
  2. Ensure token has access to the repository
  3. Verify URL format: `https://api.github.com/repos/OWNER/REPO/dispatches`

---

## Security Best Practices

### 1. GitHub Token Security
- ✅ Use a dedicated token for automation
- ✅ Limit token scopes to minimum required (`repo`, `workflow`)
- ✅ Store token in n8n credentials vault
- ✅ Rotate tokens regularly (every 90 days)
- ❌ Never commit tokens to code
- ❌ Never share tokens in plain text

### 2. Repository Permissions
- ✅ Limit who can trigger workflows
- ✅ Use branch protection rules
- ✅ Require PR reviews before merging
- ✅ Enable status checks

### 3. n8n Workflow Security
- ✅ Use webhook authentication
- ✅ Validate input data
- ✅ Log workflow executions
- ✅ Set up error notifications

---

## Advanced Features

### Custom PR Templates
Modify the workflow to use PR templates:

```yaml
- name: Read PR template
  run: |
    TEMPLATE=$(cat .github/PULL_REQUEST_TEMPLATE.md)
    BODY="${{ steps.vars.outputs.body }}\n\n$TEMPLATE"
    echo "body=$BODY" >> $GITHUB_OUTPUT
```

### Auto-assign Reviewers
Add reviewers automatically:

```yaml
- name: Request reviewers
  run: |
    gh pr edit $PR_NUMBER --add-reviewer user1,user2
```

### Add Labels
Automatically label PRs:

```yaml
- name: Add labels
  run: |
    gh pr edit $PR_NUMBER --add-label "automated,sprint-3"
```

### Link to Issues
Reference issues in PR body:

```json
{
  "body": "Closes #123\n\n{{ $json.description }}"
}
```

---

## Example n8n Nodes Configuration

### Node 1: HTTP Request (Create PR)

**Settings:**
```
Name: Create GitHub PR
Method: POST
URL: https://api.github.com/repos/Kratosgado/pms/dispatches
Authentication: Header Auth
  Name: Authorization
  Value: Bearer {{$credentials.githubToken}}
Send Body: Yes
Body Content Type: JSON
Specify Body: Using Fields Below
```

**Body (JSON):**
```json
{
  "event_type": "create-pr",
  "client_payload": {
    "title": "={{$json.title}}",
    "body": "={{$json.description}}",
    "source_branch": "={{$json.source}}",
    "target_branch": "={{$json.target}}",
    "draft": false
  }
}
```

### Node 2: Success Notification

**IF Success:**
```
Send Slack message: "✅ PR created: {{$json.title}}"
```

**IF Error:**
```
Send alert: "❌ Failed to create PR: {{$json.error}}"
```

---

## Testing

### Test Manually First
Before setting up n8n automation, test manually:

1. Go to GitHub Actions
2. Run workflow manually
3. Verify PR is created correctly
4. Check validation works (try invalid branches)

### Test with n8n
1. Create a test branch
2. Trigger n8n workflow
3. Verify PR is created
4. Check error handling (try creating duplicate)

### Validate Behavior
- ✅ PR created with correct title/body
- ✅ Source and target branches correct
- ✅ Duplicate detection works
- ✅ Error messages are clear

---

## Monitoring and Maintenance

### Check Workflow Runs
- GitHub Actions tab shows all runs
- Review logs for errors
- Monitor success rate

### Token Expiration
- Tokens expire after 90 days (default)
- Set calendar reminder to rotate
- Test after rotation

### Workflow Updates
- Keep actions versions updated
- Test changes in dev repository first
- Document workflow modifications

---

## Support and Resources

### GitHub Documentation
- [Repository Dispatch Events](https://docs.github.com/en/rest/repos/repos#create-a-repository-dispatch-event)
- [GitHub Actions](https://docs.github.com/en/actions)
- [Personal Access Tokens](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token)

### n8n Documentation
- [HTTP Request Node](https://docs.n8n.io/integrations/builtin/core-nodes/n8n-nodes-base.httprequest/)
- [GitHub Trigger](https://docs.n8n.io/integrations/builtin/trigger-nodes/n8n-nodes-base.githubtrigger/)
- [Credentials](https://docs.n8n.io/credentials/)

---

## Quick Reference

### Minimal n8n Request
```bash
curl -X POST \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Accept: application/vnd.github.v3+json" \
  -d '{
    "event_type": "create-pr",
    "client_payload": {
      "title": "My PR Title",
      "body": "My PR Description"
    }
  }' \
  https://api.github.com/repos/OWNER/REPO/dispatches
```

### Full n8n Request
```bash
curl -X POST \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Accept: application/vnd.github.v3+json" \
  -d '{
    "event_type": "create-pr",
    "client_payload": {
      "title": "Sprint 3: New Features",
      "body": "## Summary\nAdds features X, Y, Z",
      "source_branch": "feature/sprint-3",
      "target_branch": "main",
      "draft": false
    }
  }' \
  https://api.github.com/repos/Kratosgado/pms/dispatches
```

---

**Workflow Created:** 2026-02-11
**Last Updated:** 2026-02-11
**Version:** 1.0.0
