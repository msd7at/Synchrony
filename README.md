# Synchrony
Synchrony : Assignment  Software Engineer II, Servicing Apps (Level 9) 
# service-synchrony-db-ops

## Overview
This project is a Spring Boot application that provides a RESTful service to manage employee data. It supports CRUD operations, caching, asynchronous processing, and retry mechanisms. It includes a robust architecture to ensure scalability and resilience.

---

## Setup Instructions

### 1. Prerequisites
- **Java Development Kit (JDK):** Version 8 or higher.
- **Maven:** Version 3.6 or higher.
- **Database:** MySQL -> https://dev.mysql.com/downloads/installer/
- **Spring Boot Framework.**
- **Redis:** Follow https://redis.io/docs/latest/operate/oss_and_stack/install/install-redis/install-redis-on-windows/

### 2. Clone the Repository
```bash
git clone repository-url
we used : git clone https://github.com/msd7at/Synchrony.git
cd "your local directory path"
```

### 3. Configure Database
- Update the `application.properties` file with your database details and add necessary configs :
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 4. Build and Run
- Build the application using Maven:
```bash
mvn clean install 
OR
USE IDE maven clean and install RUN
```
- Run the application:
```bash
java -jar target/e# service-synchrony-db-ops
OR
use IDE run spring boot Project
```

### 5. Test the Application
- Access the APIs using tools like Postman or cURL.
- Example: Fetch all employees:
```bash
Postman API collection
https://drive.google.com/file/d/1S0qp6m1_-3MSV3MxRP1R-C9IeqT3proQ/view?usp=drive_link
```

---

## Code Architecture

### 1. Package Structure
```plaintext
com.synchrony.assignment
├── configurations       # Configuration classes for async processing and retry logic
├── controller           # Exception handling using a global controller advice
├── model                # Data model for Employee
├── repository           # Data access layer (JPA Repository)
├── service              # Business logic layer
├── tests                # Unit tests for service layer
```

### 2. Key Components

#### a. Service Layer
- **`EmployeeService`:**
    - Contains business logic for CRUD operations.
    - Implements caching using Spring Cache (`@Cacheable`, `@CacheEvict`).
    - Provides asynchronous task execution with `@Async`.

#### b. Repository Layer
- **`EmployeeRepository`:**
    - Extends `JpaRepository` to interact with the database.
    - Automatically provides basic CRUD operations.

#### c. Configuration Layer
- **`AsyncConfig`:**
    - Configures a thread pool executor for handling asynchronous tasks.
- **`RetryConfig`:**
    - Sets up retry policies with exponential backoff for handling transient failures.

#### d. Controller Advice
- **`GlobalExceptionHandler`:**
    - Centralized exception handling.
    - Uses Spring Retry to retry operations on failure.

#### e. Testing
- **`EmployeeServiceTest`:**
    - Unit tests for `EmployeeService` using Mockito for mocking dependencies.

### 3. Annotations Used
- **`@Service`:** Marks the class as a service component.
- **`@Transactional`:** Ensures transactional behavior for database operations.
- **`@Cacheable`:** Caches the results of methods to improve performance.
- **`@CacheEvict`:** Clears caches to maintain consistency after data updates.
- **`@Async`:** Enables asynchronous execution.
- **`@EnableAsync`:** Activates async processing.
- **`@EnableRetry`:** Activates retry logic.

---

## Caching
- Caching is implemented using Spring Cache with the following annotations:
    - **`@Cacheable`:** Caches the method results (e.g., `getEmployeeById`).
    - **`@CacheEvict`:** Clears all cache entries on data modification.
- Recommended caching provider: **Redis** for distributed caching in production.

---

## Concurrency and Asynchronous Processing
- Configured via `AsyncConfig` with a thread pool executor:
    - Core Pool Size: 5
    - Max Pool Size: 10
    - Queue Capacity: 25
- Example usage: `performConcurrentTask` method in `EmployeeService`.

---

## Retry Mechanism
- Configured via `RetryConfig` with:
    - **Exponential Backoff:** Starts with a 500ms interval and doubles on each retry up to 5 seconds.
    - **Retry Attempts:** Maximum of 3 retries.

---

## Testing
- **Framework:** JUnit 5.
- **Mocking Library:** Mockito.
- Tests included for:
    - Fetching employees by ID.
    - Fetching all employees.
    - Adding, updating, and deleting employees.
- **Execution:** Run tests with:
```bash
mvn test
```

---