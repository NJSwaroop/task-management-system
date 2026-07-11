# Task Management System API

A robust, production-ready backend REST API built with Spring Boot 4.x, designed for organizing and tracking daily workflows. The application features built-in database lifecycle hooks, field-level validation, custom security exception pipelines, and interactive API documentation.

## Key Features

* **Dynamic Data Fallbacks:** Automated task default states (e.g., “PENDING” “IN_PROGRESS” “COMPLETED” status, dynamic due dates, and auto-updated timestamps) handled cleanly at the persistence tier via JPA ‘@PrePersist’ and ‘@PreUpdate’ hooks.
* **Resilient Field Updates:** Safe ‘PUT’ pipeline architecture that validates and selectively merges partial updates to protect historical database records from accidental null overwrites.
* **Filter-Chain Security:** Protected state-changing mutations (‘POST’, ‘PUT’, ‘PATCH’, ‘DELETE’) secured behind HTTP Basic Authentication, while read-only mappings (‘GET’) remain publicly accessible.
* **Custom Authentication Entry Point:** Intercepts authentication failures at the filter-wall to return unified, user-friendly JSON blocks instead of blank standard container error pages.
* **Interface-Driven API Documentation:** Complete structural separation of Swagger/OpenAPI specifications using Java interfaces, keeping controller classes clean and maintainable.

## Prerequisites & Tech Stack

Before building and running the application, ensure you have the following installed:

* **Java Development Kit (JDK):** Version 17 or higher
* **Build Tool:** Maven 3.4+
* **IDE:** Spring Tool Suite (STS) or IntelliJ IDEA

### Core Framework Dependencies
* Spring Boot 4.x Starter Web
* Spring Boot Starter Security
* Spring Boot Starter Data JPA
* H2 In-Memory Database
* Lombok
* Springdoc OpenAPI Starter WebMVC UI (v2.5.0)

---

## How to Run the Application

### Option 1: Via Spring Tool Suite (STS)
1. Open STS and choose **File** -> **Import** -> **Existing Maven Projects**.
2. Browse to the root folder containing the project's `pom.xml` and click **Finish**.
3. Right-click the root project folder in the Package Explorer view.
4. Navigate to **Run As** -> **Spring Boot App**.

### Option 2: Via Terminal / Command Line
Navigate to the root directory of the project where `pom.xml` resides and run the following commands:

bash
# Clean previous builds and compile the application
mvn clean install

# Boot up the Spring Boot server
mvn spring-boot:run
## Default Test Credentials

The server will start up locally on `http://localhost:8080`. The system initializes with a pre-configured in-memory administrative user account for Basic Authentication testing:

* **Username:** `swaroop`
* **Password:** `password`
* **Assigned Role:** `ROLE_ADMIN`

## API Endpoint Documentation & Testing

### 1. Interactive Testing via Swagger UI
The application uses Interface-Driven OpenAPI specs to isolate documentation concerns out of core controllers. To explore schemas, execute live database mutations, and view model attributes in real-time, launch your web browser and open: 

 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

**How to Authenticate inside Swagger UI:**
* Click the bright green **Authorize ** button located at the top-right corner.
* Enter the test credentials (`swaroop` / `password`).
* Click **Authorize**, then select **Close**.
* All lock indicators will change to closed padlocks. You can now test state-changing requests inside the dashboard by hitting **Try it out** -> **Execute**.

### 2. Manual Testing Matrix via Postman
To execute testing scenarios via Postman, configure each request as detailed below:
* **Authorization Tab:** Select Type as **Basic Auth** and input `swaroop` and `password`.
* **Headers Tab:** Ensure `Content-Type: application/json` is added automatically for body payloads.

## Endpoint Testing Matrix

### POST `/api/tasks`
* **Request Body:** Provide JSON with only `"title"` and `"description"`. Omit `status` and `dueDate`.
* **Authentication:** Required
* **Expected Response:** `201 Created` with initialized timestamps & `PENDING` status.

---

### GET `/api/tasks`
* **Request Body:** None
* **Authentication:** Public (No Auth)
* **Expected Response:** `200 OK` returning an array of tasks.

---

### GET `/api/tasks/{id}`
* **Request Body:** None (Pass valid numeric database ID in path)
* **Authentication:** Public (No Auth)
* **Expected Response:** `200 OK` returning matching single task object.

---

### PUT `/api/tasks/{id}`
* **Request Body:** Provide JSON fields to modify. Omit fields you wish to preserve.
* **Authentication:** Required
* **Expected Response:** `200 OK` returning task with updated text and advanced `updatedAt` field.

---

### PATCH `/api/tasks/{id}/complete`
* **Request Body:** None (Leave body completely empty)
* **Authentication:** Required
* **Expected Response:** `200 OK` with status changed dynamically to `COMPLETED`.

---

### DELETE `/api/tasks/{id}`
* **Request Body:** None
* **Authentication:** Required
* **Expected Response:** `204 No Content` confirming permanent database row deletion.
## Edge Case Behaviour & Validation Testing

The API handles edge cases gracefully, returning structured JSON blocks for failures:

### Validation Error Triggers (`POST` / `PUT`)
* **Blank Title Check:** Pass `"title": " "` or omit the field entirely. The API will intercept the request and return an **HTTP 400 Bad Request** with the message: `"Title is required and cannot be blank"`.
* **Past Due Dates Check:** Provide a historical date inside the `"dueDate"` string property. The system returns an **HTTP 400 Bad Request** explicitly stating: `"Due date must be in present or future"`.

### Resource Missing Triggers (`GET` / `PUT` / `PATCH` / `DELETE`)
* Pass a non-existent lookup ID value (e.g., `/api/tasks/999`). The application throws a custom exception layout resulting in an **HTTP 404 Not Found** payload stating: `"Task not found with Id: 999"`.

## H2 Database Console Access

For local database row inspection and directly running SQL scripts:

* **Console URL:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
* **JDBC URL:** `jdbc:h2:mem:testdb`
* **Configuration:** Leave password fields empty and choose **Connect**.

## Author
Developed by Swaroop
