# Book Service

![Estado de las pruebas](https://github.com/victorhtorres/book-service/actions/workflows/ci.yml/badge.svg?branch=unit-test-ci)

## Overview

This is a Spring Boot microservice for managing book-related operations. It includes RESTful API endpoints for CRUD operations on book data, such as creating, updating, retrieving, and deleting book records.

## Features

- REST API for book management.
- CRUD operations.
- Spring Boot framework.
- Maven build system.
- H2 in-memory database (default) for development.
- Docker support.

## Prerequisites

- **Java 21** or higher
- **Maven 3.8.8+**
- **Docker** (optional, for containerization)

## Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/victorhtorres/book-service.git
   cd book-service
   ```

2. Build the project using Maven:

   ```bash
   mvn clean install
   ```

3. Run the application using Maven:

   ```bash
   mvn spring-boot:run
   ```

4. Access the API at `http://localhost:8080`.

## Running the JAR

To build and run the application as a JAR file:

1. Package the application:

   ```bash
   mvn clean package
   ```

2. Run the JAR file:

   ```bash
   java -jar target/book-service-1.0.0.jar
   ```

## Running with Docker

1. Build the Docker image:

   ```bash
   docker build -t book-service .
   ```

2. Run the Docker container:

   ```bash
   docker run -p 8080:8080 book-service
   ```

## API Endpoints

| Method | Endpoint             | Description                   |
|--------|----------------------|-------------------------------|
| GET    | `/api/v1/books`      | Retrieve all books            |
| GET    | `/api/v1/books/{id}` | Retrieve a specific book      |
| POST   | `/api/v1/books`      | Create a new book             |
| PUT    | `/api/v1/books/{id}` | Update an existing book       |
| DELETE | `/api/v1/books/{id}` | Delete a book                 |

## Database

By default, the project uses an in-memory H2 database for local development. You can access the H2 console at `http://localhost:8080/h2-console` (username: `sa`, password: empty).

To configure a persistent database, update the `application.properties` file with your preferred database settings.

## Testing

To run the unit and integration tests, use the following command:

```bash
mvn test
```

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

---

You can adjust this `README.md` according to specific details in the project, such as additional setup, configurations, or important notes for deployment.