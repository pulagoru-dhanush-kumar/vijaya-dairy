# 🥛 Vijaya Dairy

### Production-Ready Dairy E-Commerce Platform

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.4-4479A1?style=flat-square&logo=mysql&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-EC2%20%2B%20RDS-FF9900?style=flat-square&logo=amazonaws&logoColor=white)
![Razorpay](https://img.shields.io/badge/Razorpay-Payment%20Gateway-02042B?style=flat-square&logo=razorpay&logoColor=white)

![Status](https://img.shields.io/badge/Status-Live%20on%20AWS-brightgreen?style=flat-square)
![Environments](https://img.shields.io/badge/Environments-3%20(Local%20|%20Staging%20|%20Production)-blue?style=flat-square)
![Endpoints](https://img.shields.io/badge/API%20Endpoints-25%2B-purple?style=flat-square)

**[🌐 Live Demo](https://tinyurl.com/messiah-vijaya-dairy-pulagoru) · [🛡️ Admin Panel](http://3.107.228.182:8081/adminpanel.html) · [📁 Source Code](https://github.com/pulagoru-dhanush-kumar/vijaya-dairy)**

</div>
Note:** Use the staging branch to run and test this project locally.**


## 📋 Table of Contents

- [Overview](#-overview)
- [Architecture](#-architecture)
- [Tech Stack](#-tech-stack)
- [Database Schema](#-database-schema)
- [API Reference](#-api-reference)
  - [User API](#-user-api-users)
  - [Product API](#-product-api-products)
  - [Cart API](#-cart-api-cart)
  - [Payment API](#-payment-api-payment)
  - [Order API](#-order-api-orders)
  - [Address API](#-address-api-apiaddress)
  - [Admin API](#-admin-api-admin)
- [Authentication](#-authentication)
- [Environments](#-environments)
- [Local Setup](#-local-setup)
- [Deployment](#-deployment)
- [Project Stats](#-project-stats)

---

## 🔍 Overview

**Vijaya Dairy** is an authorized Vijaya Dairy outlet's production-grade full-stack e-commerce platform. It enables customers to browse dairy products, manage a cart, complete orders via Razorpay, and track delivery — backed by a secure Spring Boot REST API deployed on AWS.

### Key Highlights
- ✅ JWT-based stateless authentication with Spring Security
- ✅ OTP verification via Gmail SMTP for registration & password reset
- ✅ Razorpay payment integration with HMAC-SHA256 signature verification
- ✅ Complete order lifecycle management (cart → checkout → payment → delivery)
- ✅ Full admin panel with product CRUD, order management & analytics
- ✅ Flyway database migrations for consistent schema across all environments
- ✅ Deployed across 3 environments: Local, Staging, Production

---

## 🏗️ Architecture

```
┌──────────────────────────────────────────────────────────────────────────────┐
│                          VIJAYA DAIRY — ARCHITECTURE                         │
│                                                                              │
│   ┌──────────────────────┐         ┌───────────────────────────────────┐    │
│   │      FRONTEND        │  HTTP   │           BACKEND                  │    │
│   │                      │ ──────► │     Spring Boot 3.2.5             │    │
│   │  HTML5 / CSS3 / JS   │         │     Spring Security + JWT         │    │
│   │  Razorpay Checkout   │ ◄────── │     Hibernate ORM + JPA           │    │
│   │  Responsive UI       │  JSON   │     Global Exception Handler      │    │
│   └──────────────────────┘         │     CORS Configuration            │    │
│                                    └───────────────┬───────────────────┘    │
│                                                    │ JDBC / HikariCP        │
│                                                    ▼                        │
│                                    ┌───────────────────────────────────┐    │
│                                    │        AWS RDS MySQL 8.4          │    │
│                                    │   8 tables | Flyway Migrations    │    │
│                                    │   Automated Backups | Connection  │    │
│                                    │   Pooling via HikariCP            │    │
│                                    └───────────────────────────────────┘    │
│                                                                              │
│   ┌────────────┐      ┌────────────┐      ┌────────────────────────────┐    │
│   │  Gmail     │      │  Razorpay  │      │       AWS EC2              │    │
│   │  SMTP      │      │  Gateway   │      │   Ubuntu 24.04 LTS         │    │
│   │  OTP/Email │      │  Payments  │      │   t2.micro • nohup JAR     │    │
│   └────────────┘      └────────────┘      └────────────────────────────┘    │
└──────────────────────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.2.5 |
| **Security** | Spring Security + JWT (JJWT) |
| **ORM** | Hibernate + Spring Data JPA |
| **Database** | MySQL 8.4 (AWS RDS) |
| **Migrations** | Flyway |
| **Connection Pool** | HikariCP |
| **Build Tool** | Maven 3.8.7 |
| **Payment** | Razorpay SDK (HMAC-SHA256) |
| **Email** | Gmail SMTP (Spring Mail) |
| **Frontend** | HTML5, CSS3, Vanilla JavaScript |
| **Server** | AWS EC2 Ubuntu 24.04 LTS (t2.micro) |
| **Database Host** | AWS RDS MySQL (ap-southeast-2) |
| **Version Control** | Git + GitHub (3-branch strategy) |

---

## 🗄️ Database Schema

```
┌─────────────┐      ┌─────────────┐      ┌──────────────────┐
│    user     │──1:1─│   address   │      │    categories    │
│─────────────│      │─────────────│      │──────────────────│
│ id (PK)     │      │ id (PK)     │      │ (lookup table)   │
│ name        │      │ user_id(FK) │      └──────────────────┘
│ email       │      │ street      │
│ mobile      │      │ city        │             ┌──────────────────┐
│ password    │      │ state       │             │    product       │
│ role        │      │ pincode     │             │──────────────────│
└──────┬──────┘      └─────────────┘             │ pid (PK)         │
       │                                          │ name             │
       │ 1:1                                      │ price            │
       ▼                                          │ quantity         │
┌─────────────┐      ┌──────────────┐             │ category         │
│    cart     │──1:N─│  cart_item   │──N:1────────│ imageurl         │
│─────────────│      │──────────────│             │ product_status   │
│ id (PK)     │      │ id (PK)      │             │ available_items  │
│ user_id(FK) │      │ cart_id (FK) │             └──────────────────┘
│ total       │      │ product_id   │                      │
└─────────────┘      │ quantity     │                      │
                     │ subtotal     │                      │
                     └──────────────┘                      │
                                                           │
       │ 1:N                                               │
       ▼                                                   │
┌─────────────┐      ┌──────────────────┐                  │
│   orders    │──N:M─│ order_products   │──────────────────┘
│─────────────│      │──────────────────│
│ id (PK)     │      │ order_id (FK)    │
│ user_id(FK) │      │ product_id (FK)  │
│ razorpay_id │      │ quantity         │
│ payment_id  │      │ price_at_order   │
│ amount      │      └──────────────────┘
│ status      │
│ created_at  │
└─────────────┘
```

**Relationships Summary:**
- `user` → `address` — One-to-One (FK: address.user_id)
- `user` → `cart` — One-to-One (FK: cart.user_id)
- `cart` → `cart_item` — One-to-Many (FK: cart_item.cart_id)
- `cart_item` → `product` — Many-to-One (FK: cart_item.product_id)
- `user` → `orders` — One-to-Many (FK: orders.user_id)
- `orders` ↔ `product` — Many-to-Many via `order_products`

## 🔐 Authentication

This project uses **JWT (JSON Web Tokens)** for stateless authentication.

```
1. User logs in → POST /users/login
2. Server validates credentials → Returns JWT token
3. Client stores token (localStorage)
4. Every protected request includes:
   Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
5. Spring Security filter validates token on every request
6. SecurityContextHolder stores authenticated user's email
7. Controllers use SecurityContextHolder to get current user
```

**Token flow in controllers:**
```java
// All protected controllers extract user like this:
String email = SecurityContextHolder.getContext().getAuthentication().getName();
// Then fetch user-specific data (cart, orders, address) by this email
```

---

## 🌐 Environments

| Environment | URL | Branch | Database |
|---|---|---|---|
| 🧪 **Staging** | `http://3.107.228.182:8081` | `staging` | `vijayadairy_staging` |
| 🚀 **Production** | `http://32.236.15.39:8080` | `main` | `vijayadairy` (RDS) |

**Demo:** https://tinyurl.com/messiah-vijaya-dairy-pulagoru  
**Admin Panel:** https://tinyurl.com/messiah-vijaya-dairy-pulagoru/adminpanel.html

**Test Credentials:**
```
Email:    dhanushvip1729@gmail.com
Password: Dhanush
```

---

## ⚙️ Local Setup

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8+
- Git

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/pulagoru-dhanush-kumar/vijaya-dairy.git
cd vijaya-dairy
git checkout develop

# 2. Create local database
mysql -u root -p
CREATE DATABASE vijayadairy_local;
EXIT;

# 3. Configure application.properties
# src/main/resources/application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/vijayadairy_local?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=create
server.port=8080
spring.flyway.enabled=true
spring.flyway.baselineOnMigrate=true

# Gmail SMTP (for OTP)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password

# 4. Build and run
mvn clean package -DskipTests
java -jar target/vijay-dairy-0.0.1-SNAPSHOT.jar
```

**App is live at:** `http://localhost:8080`

---

## 🚀 Deployment

### CI/CD Workflow (Manual Git-Based)

```
Developer (Local)
      │
      ├── git push origin develop
      │
      ▼
GitHub (develop branch)
      │
      ├── Pull to Staging EC2 → test on http://3.107.228.182:8081
      │
      ▼
GitHub (staging branch) ← git merge develop
      │
      ├── Pull to Production EC2 → live
      │
      ▼
GitHub (main branch) ← git merge staging
```

### Deploy to Production

```bash
# SSH into production server
ssh -i vijaya-dairy-key.pem ubuntu@32.236.15.39

# Navigate to project
cd /home/ubuntu/vijaya-dairy

# Pull latest changes
git pull origin main

# Build
mvn clean package -DskipTests

# Restart app
pkill -f vijay-dairy
sleep 2
nohup java -jar target/vijay-dairy-0.0.1-SNAPSHOT.jar > app.log 2>&1 &

# Monitor logs
tail -f app.log
```

---

## 📊 Project Stats

| Metric | Value |
|---|---|
| **Total Commits** | 20+ |
| **Lines of Code** | 2,000+ |
| **Controllers** | 8 |
| **API Endpoints** | 25+ |
| **Database Tables** | 8 |
| **Git Branches** | 3 (develop / staging / main) |
| **Deployment Environments** | 3 |
| **Hours Invested** | 100+ |

---

## 🗺️ Roadmap

- [ ] 🔄 GitHub Actions CI/CD (auto-deploy on push)
- [ ] 🌍 Custom domain + HTTPS (AWS Certificate Manager)
- [ ] 🔐 Two-Factor Authentication (2FA)
- [ ] 📊 Admin analytics dashboard with charts
- [ ] ☁️ Dockerize application + Kubernetes deployment
- [ ] 📱 React Native mobile app

---

## 👤 Author

**Dhanush Kumar Pulagoru**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=flat-square&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/dhanush-kumar-840242212/)
[![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=github&logoColor=white)](https://github.com/pulagoru-dhanush-kumar)
[![Portfolio](https://img.shields.io/badge/Portfolio-FF5722?style=flat-square&logo=google-chrome&logoColor=white)](https://pulagoru-dhanush-kumar.github.io/Protfolio/)
[![Email](https://img.shields.io/badge/Email-D14836?style=flat-square&logo=gmail&logoColor=white)](mailto:dhanushvip1729@gmail.com)

---

<div align="center">

**⭐ If this project helped you, please give it a star!**

*Built with ❤️ by Dhanush Kumar — Java Backend Developer*

</div>
