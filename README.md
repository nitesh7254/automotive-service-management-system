# Automotive Service Management System

A backend application built with Java and Spring Boot to manage customers, vehicles, service records, and service appointments. It provides REST APIs for common automotive service operations and includes a rule-based customer support feature that identifies user intents using keywords and extracts details such as vehicle registration numbers, service types, and dates.

## Features

- **Customer Management** — create, retrieve, update, and delete customer records.
- **Vehicle Management** — manage vehicles and their registration details.
- **Service Record Management** — maintain vehicle service history.
- **Appointment Management** — book, view, reschedule, cancel, and update service appointments.
- **Rule-Based Customer Support** — classify support requests using keyword-based intent detection and predefined responses.
- **Data Validation** — validate incoming request data using Jakarta Bean Validation.
- **REST API Architecture** — expose backend functionality through HTTP endpoints.
- **PostgreSQL Persistence** — store application data in a relational database.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming language |
| Spring Boot | Application framework |
| Spring Web | REST API development |
| Spring Data JPA | Database access |
| Hibernate | ORM implementation |
| PostgreSQL | Relational database |
| Maven | Build and dependency management |
| Jakarta Bean Validation | Request validation |
| Postman | API testing, depending on project configuration |

## Project Structure

```text
src/
└── main/
    ├── java/
    │   └── com/nitesh/autosupport/
    │       ├── controller/    # REST controllers
    │       ├── service/       # Business logic
    │       ├── repository/    # Data access
    │       ├── model/         # JPA entities
    │       ├── ai/            # Rule-based support and extraction logic
    │       ├── exception/     # Exception handling
    │       └── validation/    # Validation components
    └── resources/
        └── application.properties
```

> The package and folder layout above reflects the current code structure. If you rename Java packages later, update this section accordingly.

## Prerequisites

Install the following before running the application:

- JDK 21 (or the Java version configured by the project)
- PostgreSQL
- Maven, or use the Maven wrapper if one is included
- An IDE such as IntelliJ IDEA or Eclipse
- Postman or another REST API client

## Setup and Run

### 1. Clone the repository

```bash
git clone https://github.com/nitesh7254/automotive-service-management-system.git
cd automotive-service-management-system
```

### 2. Create the PostgreSQL database

Open PostgreSQL or pgAdmin and create the database:

```sql
CREATE DATABASE automotive_ai_support;
```

The database name above matches the original project configuration. If you change the database name in `application.properties`, create a database with that same name.

### 3. Configure the database connection

Open `src/main/resources/application.properties` and confirm the PostgreSQL settings. For example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/automotive_ai_support
spring.datasource.username=YOUR_POSTGRES_USERNAME
spring.datasource.password=YOUR_POSTGRES_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Replace the username and password with your local PostgreSQL credentials. **Do not commit real passwords, API keys, or other secrets to GitHub.** For a shared or production environment, use environment variables and an appropriate schema-migration strategy.

### 4. Run the application

From the project root, run:

```bash
mvn spring-boot:run
```

Alternatively, run the main Spring Boot application class from your IDE.

The application is configured to use the default Spring Boot port unless changed in `application.properties`:

```text
http://localhost:8080
```

## API Overview

The application exposes REST endpoints for customer, vehicle, service record, appointment, and support operations. The exact endpoint paths and request formats are defined in the controller classes.

| Area | Example operations |
|---|---|
| Customers | Create, view, update, and delete customers |
| Vehicles | Manage customer vehicles |
| Service records | View and maintain service history |
| Appointments | Book, view, reschedule, cancel, and update appointments |
| Support requests | Submit and retrieve support requests |
| AI support | Submit a message for rule-based intent detection and a predefined response |

### Support endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| `POST` | `/api/support` | Submit a customer support request |
| `GET` | `/api/support` | Retrieve support requests |
| `POST` | `/api/ai/support` | Process a message using the rule-based support logic |

Use Postman or another REST client to test the endpoints. Check the controller classes for the required JSON fields and response formats.

## How the Rule-Based Support Works

The support module uses programmed rules rather than a trained machine-learning model or an external large language model API. Depending on the message, it can:

1. Identify a likely intent using keywords.
2. Extract details such as vehicle registration numbers, dates, and service types.
3. Use application and database logic to handle supported requests.
4. Return a predefined response and, where applicable, save the support request.

This is a rule-based intent-detection feature; it should not be described as an LLM-powered chatbot.

## Testing

- Test REST endpoints with Postman or another API client.
- Verify successful and invalid requests.
- Check database records after create, update, and delete operations.
- Test appointment workflows such as booking, rescheduling, and cancellation.
- Test support messages with different intents and missing details.

## Future Improvements

- Add authentication and role-based authorization.
- Improve intent matching and add automated tests for edge cases.
- Add API documentation with Swagger/OpenAPI if not already configured.
- Use environment variables for configuration and a database migration tool for schema changes.
- Build a frontend for customers and service staff.

## Author

**Nitesh Kumar**

- GitHub: [@nitesh7254](https://github.com/nitesh7254)

## License

Add a license file if you intend to distribute this project for reuse. Until a license is added, do not assume others have permission to reuse the code.
