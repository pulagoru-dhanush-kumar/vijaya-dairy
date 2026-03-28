# Vijaya Dairy - E-Commerce Platform

A full-stack e-commerce web application for a dairy products business,
built with Spring Boot and deployed on AWS.

## 🔗 Live Demo
**Staging:** http://3.107.228.182:8081/index.html

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.2.5 |
| Security | Spring Security, JWT |
| Database | MySQL 8.4 (AWS RDS) |
| ORM | Hibernate / Spring Data JPA |
| Migrations | Flyway |
| Frontend | HTML5, CSS3, Vanilla JavaScript |
| Build Tool | Maven |
| Deployment | AWS EC2 (Ubuntu 24.04) |
| Email | Gmail SMTP |

---

## ✅ Features

**User Side**
- User registration and login with JWT authentication
- Password reset via email OTP
- Browse and filter dairy products by category
- Add to cart, update quantity, remove items
- Razorpay payment integration (test mode)

**Admin Side**
- Add, edit, and delete products
- Manage product status (Active/Inactive)
- View all registered users

---

## 📁 Project Structure
```
src/main/java/com/uday/vijayadairy/
├── configuration/      # JWT, Security, CORS config
├── controller/         # REST API endpoints
├── dto/                # Request/Response objects
├── exceptions/         # Global exception handling
├── model/              # JPA Entities
├── repository/         # Spring Data JPA repositories
└── service/            # Business logic
```

---

## 🚀 Run Locally

### Prerequisites
- Java 17
- Maven 3.8+
- MySQL 8.x

### Steps
```bash
# Clone the repo
git clone https://github.com/pulagoru-dhanush-kumar/vijaya-dairy.git
cd vijaya-dairy

# Create local database
mysql -u root -p
CREATE DATABASE vijayadairy_local;
EXIT;

# Create application.properties (not tracked in git)
# See application.properties.example for reference

# Run
mvn spring-boot:run
```

App runs at `http://localhost:8080`

---

## ⚙️ Configuration

Create `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/vijayadairy_local
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email
spring.mail.password=your_app_password

server.port=8080
```

---

## 📡 Key API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/user/register` | User registration |
| POST | `/user/login` | Login, returns JWT |
| POST | `/user/forgot-password` | Send OTP to email |
| POST | `/user/reset-password` | Reset with OTP |
| GET | `/products` | Get all products |
| POST | `/cart/add` | Add item to cart |
| GET | `/cart` | View cart |
| POST | `/payment/create-order` | Razorpay order |

---

## 🌐 Deployment

Deployed on AWS EC2 with MySQL on AWS RDS.

- **EC2:** Ubuntu 24.04 LTS (t2.micro)
- **RDS:** MySQL 8.4 (db.t3.micro)
- **Region:** ap-southeast-2 (Sydney)
- **Branches:** `main` → production, `staging` → staging, `develop` → local dev

---

## 📌 What's Pending

- My Orders page (in progress)
- Razorpay live mode (KYC under review)
- Email notifications on order placement

---

## 👨‍💻 Author

**Dhanush Kumar Pulagoru**
[LinkedIn](https://www.linkedin.com/in/dhanush-kumar-840242212) •
[GitHub](https://github.com/pulagoru-dhanush-kumar) •
[Portfolio](https://pulagoru-dhanush-kumar.github.io/Protfolio/)
