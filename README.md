#  E-Commerce Backend System

A scalable backend system for an e-commerce platform built using Spring Boot.
This project demonstrates real-world backend development concepts like REST APIs, database integration, layered architecture, and order management.

---

## 🚀 Tech Stack

* Java
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Lombok

---

## 📌 Features

### 👤 User Module

* Register user
* Fetch all users
* Unique email validation

### 📦 Product Module

* Add product
* Get all products
* Get product by ID

### 🛒 Cart Module

* Add product to cart
* View user cart
* Remove item from cart

### 🧾 Order Module

* Place order from cart
* View order history
* Automatic cart clearing after order

---

## 🧱 Project Structure

```
com.ecommerce.backend
│── controller
│── service
│── repository
│── entity
│── dto
│── exception
```

---

## ⚙️ API Endpoints

### 🔹 Product APIs

* `POST /products` → Add product
* `GET /products` → Get all products
* `GET /products/{id}` → Get product by ID

### 🔹 User APIs

* `POST /users/register` → Register user
* `GET /users` → Get all users

### 🔹 Cart APIs

* `POST /cart` → Add to cart
* `GET /cart/{userId}` → View cart
* `DELETE /cart/{id}` → Remove item

### 🔹 Order APIs

* `POST /orders/{userId}` → Place order
* `GET /orders/{userId}` → Order history

---

## 🗄️ Database Configuration

Update `application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

---

## ▶️ How to Run

1. Clone the repository
2. Open in IntelliJ / Eclipse
3. Configure PostgreSQL
4. Run `BackendApplication.java`
5. Test APIs using Postman

---

## 🎯 Learning Outcomes

* REST API development
* Spring Boot architecture
* Database integration using JPA
* DTO & layered architecture
* Real-world backend flow (Cart → Order)

---

## 🚀 Future Enhancements

* JWT Authentication (Spring Security)
* Role-based access (Admin/User)
* Payment integration
* Microservices architecture
* Docker deployment

---

