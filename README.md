# 📚 Library Management System - Spring Boot Backend

A robust, production-ready RESTful API backend for a Library Management System built with **Spring Boot 3.x**. It features Role-Based Access Control (RBAC), JWT Authentication, Redis Caching, and comprehensive unit testing.

---

## 🚀 Tech Stack

* **Core:** Java 17+, Spring Boot 3.4.2
* **Security:** Spring Security, JWT (JSON Web Tokens)
* **Database & ORM:** PostgreSQL, Spring Data JPA, Hibernate
* **Caching:** Redis Cache (`spring-boot-starter-cache`)
* **Testing:** JUnit 5, Mockito, MockMvc
* **Validation & Utilities:** Jakarta Bean Validation, Lombok, Maven

---

## ✨ Key Features

1. **Role-Based Access Control (RBAC):**
   * `ROLE_USER`: Can view books, borrow/return books, and view personal borrow history.
   * `ROLE_ADMIN`: Can add/update/delete books, manage users, and view all borrow records across the system.
2. **JWT Authentication & Authorization:** Secure stateless authentication endpoint mechanism.
3. **High-Performance Caching:** Integrated **Redis Caching** on Book and User services to minimize database hits and enhance response time.
4. **Automated Inventory Management:** Automatic stock deduction when a book is borrowed and stock restoration upon return.
5. **Global Exception Handling:** Custom exceptions (`ResourceNotFoundException`) handled centrally via `@RestControllerAdvice`.
6. **Unit & Integration Testing:** Tested services and controllers using JUnit 5, Mockito, and MockMvc.

---

## 📂 Project Architecture

```text
src/main/java/com/LearningSpring/LearningAPI/
│
├── Controller/         # REST Controllers (Book, User, Borrow)
├── Service/            # Business Logic Layer
├── Repository/         # Spring Data JPA Repositories
├── Entity/             # Database Entities (User, Book, Borrow)
├── DTO/                # Request and Response DTOs
├── Security/           # JWT Filters and Security Config
└── Exception/          # Global Exception Handler & Custom Exceptions
