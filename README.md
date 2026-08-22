# Automotive AI Customer Support

An AI-powered automotive customer support backend built using Spring Boot, Spring Data JPA, PostgreSQL, and REST APIs.

The system manages customers, vehicles, and service appointments and provides an AI support API that can understand customer requests related to vehicle information and service appointments.

---

## Features

### Customer Management

- Create customer
- Get all customers
- Get customer by ID
- Update customer
- Delete customer
- Customer input validation

### Vehicle Management

- Create vehicle
- Get all vehicles
- Get vehicle by ID
- Get vehicle by registration number
- Update vehicle
- Delete vehicle
- Unique vehicle registration number
- Vehicle input validation

### Appointment Management

- Create appointment
- Get all appointments
- Get appointment by ID
- Get appointments by customer
- Get appointments by vehicle
- Update appointment
- Cancel appointment
- Reschedule appointment
- Delete appointment
- Appointment validation
- Real calendar-date validation

### AI Customer Support

The AI support layer can handle:

- Vehicle information
- Customer information
- Appointment information
- Appointment cancellation
- Appointment rescheduling
- Vehicle not found responses
- Appointment not found responses
- Customer not found responses
- Greeting messages

---

## Technologies Used

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Jakarta Bean Validation
- REST API
- Eclipse / Spring Tool Suite
- Postman

---

## Project Architecture

```text
Client / Postman
       |
       v
REST Controller
       |
       v
Service Layer
       |
       v
Repository Layer
       |
       v
PostgreSQL Database