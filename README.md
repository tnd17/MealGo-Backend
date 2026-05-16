# MealGo Backend

MealGo Backend is the server-side application for the MealGo food ordering system.

This project provides RESTful APIs for managing food ordering operations, including authentication, food management, cart handling, order processing, voucher management, guest checkout, and email payment confirmation.

---

## Features

- User authentication (Register/Login)
- Role-based authorization (Admin/User)
- Food management (CRUD)
- Shopping cart management
- Order placement
- Guest checkout
- Voucher/discount support
- Order tracking
- Email payment confirmation
- Food image upload

---

## Tech Stack

- Java 21
- Spring Boot 4
- Spring Security
- Spring Data JPA
- MySQL
- Maven
- Docker
- Postman

---

## Project Structure

src/main/java/com/mealgo/backend

- controller
- service
- repository
- entity
- config
- dto

---

## Running Locally

1. Clone repository

```bash
git clone https://github.com/tnd17/MealGo-Backend.git
```

2. Run project

```bash
./mvnw spring-boot:run
```

---

## Running with Docker

From root project folder:

```bash
docker compose up --build
```

---

## API Testing

API endpoints were tested using Postman.

Example:

- GET /api/foods
- POST /api/orders
- POST /api/auth/login

---

## Author

Developed as a university course project.