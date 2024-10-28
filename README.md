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

