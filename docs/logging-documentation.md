# Logging Documentation - PMS Application

## Overview

The PMS application uses **SLF4J** (Simple Logging Facade for Java) with **Logback** as the logging implementation. This provides comprehensive logging for authentication, CRUD operations, and error tracking.

---

## Logging Framework

### Dependencies

```xml
<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-api</artifactId>
  <version>2.0.9</version>
</dependency>
<dependency>
  <groupId>ch.qos.logback</groupId>
  <artifactId>logback-classic</artifactId>
  <version>1.4.11</version>
</dependency>
```

### Configuration File

**Location:** `src/main/resources/logback.xml`

---

## Log Outputs

### 1. Console Output (INFO level)
- Real-time log display during application execution
- Useful for development and debugging
- Format: `timestamp [thread] level logger - message`

### 2. Application Log File (All levels)
- **File:** `logs/app.log`
- **Rotation:** Daily (keeps last 30 days)
- **Max Size:** 100MB total
- **Contains:** All log messages (INFO, WARN, ERROR)

### 3. Error Log File (ERROR level only)
- **File:** `logs/error.log`
- **Rotation:** Daily (keeps last 30 days)
- **Contains:** Only ERROR level messages
- **Purpose:** Quick error investigation

---

## Log Format

```
2026-02-11 09:15:23 [main] INFO  c.k.pms.utils.context.AuthManager - Authentication attempt for user: john@example.com
```

**Format Breakdown:**
- `2026-02-11 09:15:23` - Timestamp
- `[main]` - Thread name
- `INFO` - Log level
- `c.k.pms.utils.context.AuthManager` - Logger (abbreviated class name)
- `Authentication attempt for user: john@example.com` - Message

---

## What Gets Logged

### Authentication Operations (AuthManager)

#### Successful Authentication
```
INFO - Authentication attempt for user: john@example.com
INFO - User successfully authenticated: John Doe (ID: US001, Role: REGULAR)
```

#### Failed Authentication - Invalid Password
```
INFO - Authentication attempt for user: john@example.com
WARN - Failed authentication attempt for user: john@example.com - Invalid password
```

#### Failed Authentication - User Not Found
```
INFO - Authentication attempt for user: nonexistent@example.com
WARN - Failed authentication attempt - User not found: nonexistent@example.com
```

#### Unauthorized Access Attempt
```
WARN - Unauthorized access attempt - Admin privileges required. Current user: John Doe
```

#### Admin Access Granted
```
DEBUG - Admin access granted for user: Jane Admin
```

### CRUD Operations (UserInMemoryDatabase)

#### User Creation
```
INFO - Creating new user: John Doe (ID: US001, Email: john@example.com, Role: REGULAR)
```

### Entity Operations (Repository)

#### Entity Update
```
INFO - Entity updated: User (ID: US001)
```

#### Entity Deletion - Success
```
INFO - Entity deleted: User (ID: US001)
```

#### Entity Deletion - Not Found
```
ERROR - Failed to delete entity - not found: ID US999
```

---

## Log Levels

### DEBUG
- Detailed diagnostic information
- Admin privilege checks
- Not shown in production (INFO level)

### INFO
- General application flow
- User actions (login, logout, create, update)
- Normal operations

### WARN
- Potential issues
- Failed authentication attempts
- Unauthorized access attempts
- Recoverable errors

### ERROR
- Critical errors
- Failed operations
- Entity not found errors
- Stack traces included

---

## Viewing Logs

### During Development (Console)

```bash
# Run application
mvn exec:java

# Logs appear in terminal
```

### Log Files

```bash
# View all logs
cat logs/app.log

# View only errors
cat logs/error.log

# Tail logs in real-time
tail -f logs/app.log

# Search logs for specific user
grep "john@example.com" logs/app.log

# View today's logs
ls logs/app-$(date +%Y-%m-%d).log
```

---

## Configuration

### Logback Configuration (logback.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- Console Appender -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- File Appender with Rotation -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/app.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>logs/app-%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxHistory>30</maxHistory>
            <totalSizeCap>100MB</totalSizeCap>
        </rollingPolicy>
    </appender>

    <!-- Root Logger -->
    <root level="INFO">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="FILE" />
    </root>
</configuration>
```

### Changing Log Level

Edit `logback.xml`:

```xml
<!-- For more verbose logging -->
<root level="DEBUG">

<!-- For less verbose logging -->
<root level="WARN">
```

---

## Log Management

### Log Rotation
- **Automatic:** Daily at midnight
- **Retention:** 30 days
- **Cleanup:** Old logs deleted automatically

### Disk Space Management
- **Total Size Cap:** 100MB
- **Per Day:** One file per day
- **Old Logs:** Deleted when cap reached

---

## Troubleshooting

### Logs Not Appearing

**Issue:** No logs in console or file

**Solutions:**
1. Check logback.xml is in `src/main/resources/`
2. Verify SLF4J and Logback dependencies in pom.xml
3. Run `mvn clean compile` to refresh resources

### Permission Denied on logs/app.log

**Issue:** Cannot write to log file

**Solutions:**
```bash
# Create logs directory
mkdir logs

# Fix permissions
chmod 755 logs
```

### Logs Too Verbose

**Issue:** Too many log messages

**Solutions:**
1. Change root level to WARN in logback.xml
2. Set specific logger levels:
```xml
<logger name="com.kratosgado.pms" level="WARN" />
```

---

## Best Practices

### DO:
✅ Log important user actions (authentication, CRUD)
✅ Log errors with context (user ID, entity ID)
✅ Use appropriate log levels
✅ Include relevant details in messages
✅ Log exceptions with stack traces

### DON'T:
❌ Log passwords or sensitive data
❌ Log in tight loops (performance impact)
❌ Use System.out.println() (use logger instead)
❌ Log too much at DEBUG level in production
❌ Leave DEBUG level enabled in production

---

## Adding Logging to New Classes

### Step 1: Import Logger

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
```

### Step 2: Create Logger Instance

```java
public class MyClass {
    private static final Logger logger = LoggerFactory.getLogger(MyClass.class);

    // ... rest of class
}
```

### Step 3: Add Log Statements

```java
public void myMethod(String param) {
    logger.info("Method called with param: {}", param);

    try {
        // ... business logic
        logger.debug("Operation successful");
    } catch (Exception e) {
        logger.error("Operation failed: {}", e.getMessage(), e);
        throw e;
    }
}
```

---

## Security Considerations

### Never Log:
- Passwords (plain or hashed)
- API keys or tokens
- Credit card numbers
- Social security numbers
- Private encryption keys

### Safe to Log:
- User IDs (non-sensitive identifiers)
- Email addresses (for authentication context)
- Entity IDs
- Operation types
- Timestamps
- Error messages (without sensitive data)

---

## Performance Impact

### Minimal Overhead
- SLF4J uses efficient placeholders: `logger.info("User: {}", userId)`
- String concatenation avoided
- Asynchronous appenders available if needed

### Production Optimization
1. Use INFO level (not DEBUG)
2. Enable file appenders only
3. Disable console appender in production
4. Use async appenders for high-throughput

---

## Summary

The logging system provides:
- **Visibility** into application behavior
- **Debugging** capabilities for development
- **Auditing** of user actions
- **Error tracking** for troubleshooting
- **Performance monitoring** through log analysis

All logs are captured with appropriate detail while maintaining security and performance.

---

**Last Updated:** 2026-02-11
**Sprint:** Sprint 1
**User Story:** US-4 - Comprehensive Logging System
**Configuration:** `src/main/resources/logback.xml`
