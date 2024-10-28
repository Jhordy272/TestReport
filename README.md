# Test Case Reporting API

This API enables the management of users, roles, projects, and test case reports. Developed in **Spring Boot**, it follows RESTful principles for easy integration with other systems.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Request Examples](#request-examples)
- [Contributing](#contributing)
- [License](#license)

## Features

- Management of users and roles.
- Project management with user associations.
- Creation and reporting of test cases for each project.
- Authentication and authorization using **JWT**.
- JSON format responses.

## Requirements

- **Language**: Java 21+
- **Framework**: Spring Boot 2.5+
- **Database**: MySQL, PostgreSQL, or another Spring Data JPA-compatible database
- **Tools**: Maven

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/Jhordy272/TestReport
    ```

2. Navigate to the project directory:

    ```bash
    cd TestReport
    ```

3. Install the required dependencies:

    ```bash
    mvn install
    ```


4. Configure the environment variables as indicated in the [Configuration](#configuration) section.

## Configuration

Set the required environment variables in a `.env` file or in `application.properties`:

```properties
# Database configuration
spring.datasource.url=jdbc:mysql://localhost:3306/test_case_reporting
spring.datasource.username=username
spring.datasource.password=password

# JWT configuration
jwt.secret=secret_key

# Server configuration
server.port=8080 
```

## Usage
To start the server, run:
```bash 
mvn spring-boot:run
```
The API will be available at http://localhost:8080.


## Endpoints

### Authentication
- `POST /auth/login`: User authentication and JWT token generation.
- `POST /auth/register`: User registration with role QA and JWT token generation.

### Role Management
- `GET /roles`: Retrieves all roles.
- `GET /roles/{id}`: Retrieves a role by ID.
- `POST /roles`: Creates a role.
- `PUT /roles/{id}`: Modifies a role by ID.
- `DELETE /roles/{id}`: Deletes a role by ID.

### User Management
- `GET /users`: Retrieves all roles.
- `GET /users/{id}`: Retrieves a role by ID.
- `POST /users`: Creates a role.
- `PUT /users/{id}`: Modifies a role by ID.
- `DELETE /users/{id}`: Deletes a role by ID.

### Project Management
- `GET /projects`: Retrieves all projects.
- `GET /projects/{id}`: Retrieves a project by ID.
- `POST /projects`: Creates a projects.
- `PUT /projects/{id}`: Modifies a project by ID.
- `DELETE /projects/{id}`: Deletes a project by ID.

### Status Management
- `GET /status`: Retrieves all status.
- `GET /status/{id}`: Retrieves a status by ID.
- `POST /status`: Creates a status.
- `PUT /status/{id}`: Modifies a status by ID.
- `DELETE /status/{id}`: Deletes a status by ID.

### Test Report Management
- `GET /reports`: Retrieves all reports.
- `GET /reports/{id}`: Retrieves a report by ID.
- `POST /reports`: Creates a report.
- `PUT /reports/{id}`: Modifies a report by ID.
- `DELETE /reports/{id}`: Deletes a report by ID.
