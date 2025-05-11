# School Vaccination Portal – Backend

This is the backend service for the School Vaccination Portal, built with **Spring Boot** and **MongoDB**.

## Features

- Manage students and vaccination drives
- Record vaccinations for each student per drive
- Generate and download vaccination reports (Excel/PDF)
- RESTful API endpoints

## Prerequisites

- Java 17+ (or your project’s Java version)
- Maven
- MongoDB (local or cloud instance)

## Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/school-vaccination-portal.git
   cd school-vaccination-portal/backend


   Configure MongoDB:
By default, the app connects to mongodb://localhost:27017/vaccination_portal.
To change, edit src/main/resources/application.properties:
spring.data.mongodb.uri=mongodb://localhost:27017/vaccination_portal
Build and run the application:
bash

Copy Code
mvn clean install
mvn spring-boot:run
API Documentation:
The API is available at http://localhost:8080/api/
(Optional) Swagger UI: http://localhost:8080/swagger-ui.html (if enabled)
Project Structure

controller/ – REST controllers
service/ – Business logic
repository/ – MongoDB repositories
model/ – Data models
Useful Commands

Run tests: mvn test
Build JAR: mvn package
Environment Variables

You can set the following in application.properties:

spring.data.mongodb.uri
server.port
