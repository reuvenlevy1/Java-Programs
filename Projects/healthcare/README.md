# Healthcare Appointment API

A Java Spring Boot REST API to manage Patients, Doctors, and Appointments with validation to prevent overlapping appointments.

---

## Table of contents

- [Overview](#overview)
- [Prerequisites & Setup (local dev)](#prerequisites%20setup%20local%20dev)
- [Application Configuration](#application%20configuration)
- [Build & Run](#build%20run)
- [API Endpoints](#api%20endpoints)
- [Postman Collection](#postman%20collection)
- [Postman Testing](#postman%20testing)
  - [Adding Patients](#adding%20patients)
  - [Retrieving Patients](#retrieving%20patients)
  - [Updating Patient](#updating%20patient)
  - [Retrieving Updated Patients](#retrieving%20updated%20patients)
  - [Delete Patient](#delete%20patient)
  - [Retrieving Patient](#retrieving%20patient)
  - [Adding Doctor](#adding%20doctor)
  - [Retrieving Doctor](#retrieving%20doctor)
  - [Add Valid Appointment](#add%20valid%20appointment)
  - [Add Invalid Appointment](#add%20invalid%20appointment)

---

## Overview

This project implements a simple appointment scheduling backend using Spring Boot, JPA, and an in-memory H2 database for local development. Highlights:

- Built using REST Architecture, MVC design pattern: Controller → Service → Repository → Entity
- Business rule: prevents overlapping appointments for the same doctor or patient (checked via repository queries)
- Runnable locally with Maven + JDK 17

---

## Prerequisites & Setup (local dev)

- Java Development Kit (JDK 17 or later)
- Apache Maven (latest stable)
- An IDE (Eclipse / IntelliJ / VS Code) — the project was created using **Eclipse IDE for Enterprise Java and Web Developers - 2022-12** (that is fine)
- Postman (optional, for API testing)

Project was created with Spring Initializr and includes:
- Spring Web
- Spring Data JPA
- H2 Database (in-memory for local dev)
- (optional) Spring Boot DevTools

> Note: Spring Boot 3.x uses Jakarta APIs (your code imports `jakarta.*`) and requires Java 17+. Confirm your environment uses Java 17 or later.

---

## Application configuration

Place this file in `src/main/resources/application.properties`. This configuration uses H2 database for in memory storage and console:

```properties
spring.application.name=HealthcareApp

# H2 in-memory DB (shared mode)
spring.datasource.url=jdbc:h2:mem:healthcaredb;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# H2 console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

---

## Build & Run

From project root (the folder that contains `pom.xml`):
```bash
# Build
mvn clean package

# Run via Maven
mvn spring-boot:run

# OR run the jar
java -jar target/<your-artifact-name>.jar
```

Server: `http://localhost:8080`
API base: `http://localhost:8080/api`
H2 console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:healthcaredb`, user: `sa`)

---

## API Endpoints

Base endpoints:
- Patients:
	- `POST /api/patients`,
	- `GET /api/patients`,
	- `GET /api/patients/{id}`,
	- `PUT /api/patients/{id}`,
	- `DELETE /api/patients/{id}`
- Doctors:
	- `POST /api/doctors`,
	- `GET /api/doctors`,
	- `GET /api/doctors/{id}`,
	- `PUT /api/doctors/{id}`,
	- `DELETE /api/doctors/{id}`
- Appointments:
	- `POST /api/appointments`,
	- `GET /api/appointments`,
	- `GET /api/appointments/{id}`,
	- `PUT /api/appointments/{id}`,
	- `DELETE /api/appointments/{id}`

If the appointment overlaps an existing one for the same doctor/patient, the API returns `400 Bad Request` with an explanatory message.

---

## Postman collection

File: `postman/healthcare-appointment-api.postman_collection.json`

How to use:
1. Import the collection in Postman (File → Import → select the JSON).
2. Set environment variable `base_url` to `http://localhost:8080/api`.
3. Run requests in order: create Patient → create Doctor → create Appointment → test overlapping appointment.

---

## Postman Testing

  - Successful POST responses (Patient/Doctor/Appointment)

### Adding Patients
![[docs/images/Pasted image 20251029221613.png]]
![[docs/images/Pasted image 20251029221737.png]]

### Retrieving Patients
![[docs/images/Pasted image 20251029221841.png]]

### Updating Patient
![[docs/images/Pasted image 20251029221922.png]]

### Retrieving Updated Patients
![[docs/images/Pasted image 20251029222025.png]]

### Delete Patient
![[docs/images/Pasted image 20251029222613.png]]

### Retrieving Patient
![[docs/images/Pasted image 20251029223027.png]]

### Adding Doctor
![[docs/images/Pasted image 20251029222217.png]]

### Retrieving Doctor
![[docs/images/Pasted image 20251029223059.png]]

### Add Valid Appointment
![[docs/images/Pasted image 20251029223131.png]]

### Add Invalid Appointment
![[docs/images/Pasted image 20251029223145.png]]
Receives a `400` response for overlapping appointment.