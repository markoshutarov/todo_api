# ✅ Todo API

A simple Todo REST API — this was my first Spring Boot project, built to learn 
the fundamentals of backend development. It helped me understand how REST APIs 
work, how to structure a Spring Boot project, and how to implement JWT authentication.

## 🛠️ Tech Stack
- Java 17
- Spring Boot 3
- Spring Security + JWT
- PostgreSQL
- JPA / Hibernate
- Maven

## ✨ Features
- JWT based registration and login
- Full CRUD operations for todos
- User-specific todos
- Input validation & exception handling

## 🚀 How to Run Locally
1. Clone the repository
```bash
git clone https://github.com/markoshutarov/todo-api.git
```
2. Create a PostgreSQL database named `todoapp`
3. Set environment variables:
```
DB_PASSWORD=your_db_password
JWT_SECRET=your_base64_secret
```
4. Run the project in IntelliJ or with:
```bash
./mvnw spring-boot:run
```
5. API is available at `http://localhost:8080`
