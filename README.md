# E-Commerce Backend System

A Spring Boot backend for an e-commerce platform with user registration, JWT authentication, role-based authorization, product management, cart management, and order placement.

This project demonstrates real-world backend development concepts such as REST APIs, layered architecture, Spring Security, database integration, validation, exception handling, and transactional order processing.

## Tech Stack

- Java 17
- Spring Boot
- Spring Web MVC
- Spring Security
- Spring Data JPA
- PostgreSQL
- H2 Database for tests
- JWT
- Lombok
- Maven

## Key Features

- User registration with BCrypt password hashing
- JWT-based login and authentication
- Role-based access control with `USER` and `ADMIN`
- Product APIs for adding and viewing products
- Cart APIs for adding, viewing, and removing cart items
- Order placement from cart
- Stock reduction after successful order
- Automatic cart clearing after order placement
- DTO-based request and response handling
- Request validation using Jakarta Validation
- Global exception handling
- Transactional order placement using `@Transactional`
- `BigDecimal` used for price and order total calculations
- Separate test profile using H2 database

## Roles And Access

### Public Access

- `POST /users/register`
- `POST /auth/login`
- `GET /products`
- `GET /products/{id}`
- `GET /hello`

### Admin Access

- `POST /products`
- `GET /users`

### User/Admin Access

- `POST /cart`
- `GET /cart/{userId}`
- `DELETE /cart/{id}`
- `POST /orders/{userId}`
- `GET /orders/{userId}`

## Project Structure

```text
com.ecommerce.backend
|-- config
|-- controller
|-- dto
|-- entity
|-- exception
|-- repository
|-- security
|-- service
```

## Authentication Flow

### 1. Register User

```http
POST /users/register
```

```json
{
  "name": "Shishank",
  "email": "user@gmail.com",
  "password": "password123"
}
```

New users are registered with the `USER` role by default.

### 2. Login

```http
POST /auth/login
```

```json
{
  "email": "user@gmail.com",
  "password": "password123"
}
```

Response:

```json
{
  "token": "JWT_TOKEN_HERE",
  "tokenType": "Bearer"
}
```

### 3. Use JWT Token

For protected APIs, send the token in the request header:

```http
Authorization: Bearer JWT_TOKEN_HERE
```

## API Endpoints

### User APIs

| Method | Endpoint | Access | Description |
| --- | --- | --- | --- |
| POST | `/users/register` | Public | Register a new user |
| GET | `/users` | ADMIN | Fetch all users |

### Auth APIs

| Method | Endpoint | Access | Description |
| --- | --- | --- | --- |
| POST | `/auth/login` | Public | Login and receive JWT token |

### Product APIs

| Method | Endpoint | Access | Description |
| --- | --- | --- | --- |
| POST | `/products` | ADMIN | Add a new product |
| GET | `/products` | Public | Fetch all products |
| GET | `/products/{id}` | Public | Fetch product by ID |

Example product request:

```json
{
  "name": "Laptop",
  "price": 59999.99,
  "quantity": 10
}
```

### Cart APIs

| Method | Endpoint | Access | Description |
| --- | --- | --- | --- |
| POST | `/cart` | USER/ADMIN | Add product to cart |
| GET | `/cart/{userId}` | USER/ADMIN | View user cart |
| DELETE | `/cart/{id}` | USER/ADMIN | Remove cart item |

Example cart request:

```json
{
  "userId": 1,
  "productId": 2,
  "quantity": 3
}
```

### Order APIs

| Method | Endpoint | Access | Description |
| --- | --- | --- | --- |
| POST | `/orders/{userId}` | USER/ADMIN | Place order from cart |
| GET | `/orders/{userId}` | USER/ADMIN | View order history |

## Database Configuration

Create a PostgreSQL database:

```sql
CREATE DATABASE ecommerce_db;
```

Configure credentials using environment variables:

```properties
DB_USERNAME=postgres
DB_PASSWORD=your_password
JWT_SECRET=your_base64_secret_key
JWT_EXPIRATION_MS=86400000
```

The application reads these values in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD}
jwt.secret=${JWT_SECRET:...}
jwt.expiration-ms=${JWT_EXPIRATION_MS:86400000}
```

## Admin User Setup

Public registration creates users with the `USER` role.

To create an admin account, register a user first and then update the role in the database:

```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'admin@gmail.com';
```

After that, login with the admin email and password to receive an admin JWT token.

## How To Run

Clone the repository:

```bash
git clone https://github.com/shishank-1/Ecommerce_backend.git
cd Ecommerce_backend
```

Set database environment variables:

```bash
DB_USERNAME=postgres
DB_PASSWORD=your_password
```

Run the application:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

## How To Run Tests

The project includes a separate test profile using H2 database, so tests do not require local PostgreSQL credentials.

```bash
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```

## Important Implementation Details

- Passwords are stored using BCrypt hashing.
- JWT is used for stateless authentication.
- Spring Security protects APIs based on user roles.
- Product prices and order totals use `BigDecimal`.
- Order placement is transactional to keep stock, order, and cart updates consistent.
- Request DTOs include validation annotations.
- Global exception handling returns clean error responses.

## Future Enhancements

- Use logged-in user from JWT instead of passing `userId` in cart/order APIs
- Add product update and delete APIs
- Add Swagger/OpenAPI documentation
- Add more service and controller tests
- Add Docker and Docker Compose setup
- Add refresh token support
- Add payment integration
- Add optimistic locking for safer stock handling

## Learning Outcomes

- REST API development with Spring Boot
- Layered backend architecture
- Spring Security with JWT
- Role-based authorization
- JPA entity relationships
- PostgreSQL integration
- DTO validation and exception handling
- Transaction management
- Test profile setup with H2
