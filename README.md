<div align="center">

<img src="https://capsule-render.vercel.app/api?type=venom&color=gradient&customColorList=12,20,30&height=250&section=header&text=🥛%20Vijaya%20Dairy&fontSize=60&fontColor=fff&fontAlignY=45&desc=Production-Ready%20Dairy%20E-Commerce%20Platform&descSize=18&descColor=c9d1d9&descAlignY=65" />

<p>
<img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
<img src="https://img.shields.io/badge/Spring%20Boot-3.2.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
<img src="https://img.shields.io/badge/MySQL-8.4-4479A1?style=for-the-badge&logo=mysql&logoColor=white" />
<img src="https://img.shields.io/badge/AWS-EC2%20%2B%20RDS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white" />
<img src="https://img.shields.io/badge/Razorpay-Payment%20Gateway-02042B?style=for-the-badge&logo=razorpay&logoColor=white" />
</p>

<p>
<img src="https://img.shields.io/badge/Status-Live%20on%20AWS-brightgreen?style=flat-square" />
<img src="https://img.shields.io/badge/Environments-3%20(Local%20%7C%20Staging%20%7C%20Production)-blue?style=flat-square" />
<img src="https://img.shields.io/badge/API%20Endpoints-25%2B-purple?style=flat-square" />
</p>

**[🌐 Live Demo](https://tinyurl.com/messiah-vijaya-dairy-pulagoru) · [🛡️ Admin Panel](https://tinyurl.com/messiah-vijaya-dairy-pulagoru/adminpanel.html) · [📁 Source Code](https://github.com/pulagoru-dhanush-kumar/vijaya-dairy)**

</div>

---

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

---

## 📡 API Reference

> **Base URL:** `http://32.236.15.39:8080`  
> **Auth:** All protected endpoints require `Authorization: Bearer <JWT_TOKEN>` header  
> **Content-Type:** `application/json`

---

### 👤 User API (`/users`)

#### `POST /users/register`
Register a new user. Sends OTP to the provided email.

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "MyPassword123",
  "confirmpassword": "MyPassword123"
}
```

**Response (200):**
```json
{
  "productStatus": true,
  "message": "otp sent successfully"
}
```

---

#### `POST /users/verifyotp`
Verify OTP to complete registration. Requires active session.

**Query Params:** `?otp=123456`

**Response (200):**
```json
{
  "productStatus": true,
  "message": "OTP verified successfully"
}
```

---

#### `POST /users/login`
Authenticate user and receive JWT token.

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "MyPassword123"
}
```

**Response (200):**
```json
{
  "productStatus": true,
  "email": "user@example.com",
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

#### `POST /users/forgotpassword`
Send OTP for password reset.

**Query Params:** `?email=user@example.com`

**Response (200):**
```json
{
  "message": "forgot password otp has been sent to the user",
  "email": "user@example.com"
}
```

---

#### `POST /users/resetpassword`
Reset password using OTP received by email.

**Query Params:** `?otp=123456&password=NewPass123&confirmpassword=NewPass123`

---

#### `GET /users/me` 🔒
Get currently authenticated user details.

**Response (200):**
```json
{
  "name": "user",
  "email": "user@example.com",
  "mobile": "9876543210"
}
```

---

#### `GET /users/all`
Get all registered users.

---

### 📦 Product API (`/products`)

#### `GET /products/all`
Get all products (public).

**Response (200):**
```json
{
  "data": [
    {
      "pid": 1,
      "name": "Curd",
      "price": 40.00,
      "imageurl": "https://...",
      "category": "CURD",
      "quantity": "500ml"
    }
  ],
  "total_size": 10,
  "productStatus": 200
}
```

---

#### `GET /products/category/all`
Get products filtered by category.

**Request Header:** `Category: CURD` *(Values: MILK, CURD, GHEE, PANNER, BUTTERMILK, etc.)*

**Response (200):**
```json
{
  "data": [...],
  "category_total": 3,
  "productStatus": 200
}
```

---

#### `POST /products/save` 🔒
Save a new product.

**Request Body:**
```json
{
  "name": "Fresh Curd",
  "price": 45.00,
  "quantity": "500ml",
  "category": "CURD",
  "imageurl": "https://...",
  "availableItems": 100
}
```

---

### 🛒 Cart API (`/cart`) 🔒

> All cart endpoints require JWT authentication. Cart is auto-scoped to the logged-in user.

#### `POST /cart/addtocart`
Add item to cart.

**Query Params:** `?productid=1&quantity=2`

**Response (200):**
```json
{
  "productStatus": true,
  "email": "user@example.com",
  "cart": { ... }
}
```

---

#### `POST /cart/increase`
Increase quantity of a cart item by 1.

**Query Params:** `?productid=1`

---

#### `POST /cart/decrease`
Decrease quantity of a cart item by 1.

**Query Params:** `?productid=1`

---

#### `POST /cart/removeproduct`
Remove product from cart entirely.

**Query Params:** `?productid=1`

---

#### `GET /cart/mycart`
Get current user's cart with all items and totals.

**Response (200):**
```json
{
  "productStatus": true,
  "email": "user@example.com",
  "cart": {
    "id": 5,
    "items": [...],
    "total": 185.00
  }
}
```

---

### 💳 Payment API (`/payment`) 🔒

#### `GET /payment/checkout`
Create a Razorpay order from the user's cart. Returns order details for frontend SDK.

**Response (200):**
```json
{
  "success": true,
  "data": {
    "razorpay_order_id": "order_xxxxx",
    "amount": 18500,
    "currency": "INR",
    "key": "rzp_test_xxxxx"
  }
}
```

---

#### `POST /payment/verify`
Verify Razorpay payment using HMAC-SHA256 signature. On success, updates order status and clears cart.

**Request Body:**
```json
{
  "razorpay_order_id": "order_xxxxx",
  "razorpay_payment_id": "pay_xxxxx",
  "razorpay_signature": "abc123..."
}
```

**Response (200):**
```json
{
  "success": true,
  "message": "Payment verified successfully"
}
```

**Response (401 — Tampered/Invalid Signature):**
```json
{
  "success": false,
  "error": "Payment verification failed - Invalid signature"
}
```

> **Security Note:** Signature is verified server-side as `HMAC_SHA256(razorpay_order_id + "|" + razorpay_payment_id, secret_key)`. This prevents payment tampering.

---

### 📋 Order API (`/orders`) 🔒

#### `GET /orders/myorders`
Get all orders of the currently authenticated user.

**Response (200):**
```json
{
  "success": true,
  "email": "user@example.com",
  "data": [
    {
      "id": 12,
      "razorpayOrderId": "order_xxxxx",
      "amount": 185.00,
      "status": "SUCCESS",
      "createdAt": "2026-03-15T10:30:00"
    }
  ]
}
```

---

#### `POST /orders/orders/{id}`
Modify order status (RETURNED or CANCELLED).

**Path Param:** `id` — Order ID  
**Query Params:** `?razorpayOrderId=order_xxxxx&status=CANCELLED`

---

### 📍 Address API (`/api/address`) 🔒

#### `POST /api/address/my-address`
Save or update delivery address for the logged-in user.

**Request Body:**
```json
{
  "street": "123 Main St",
  "city": "Hyderabad",
  "state": "Telangana",
  "pincode": "500001",
  "country": "India"
}
```

---

#### `GET /api/address/my-address`
Get delivery address of the logged-in user.

**Response (200):** Returns saved `Address` object.  
**Response (404):** `"No address found for your account"`

---

### 🛡️ Admin API (`/admin`) 🔒

> Admin-only endpoints. Requires admin role in JWT.

#### Product Management

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/admin/products` | Get all products |
| `GET` | `/admin/products/{pid}` | Get product by ID |
| `GET` | `/admin/products/status/{status}` | Filter by status (ACTIVE / INACTIVE / DRAFT) |
| `POST` | `/admin/products` | Add new product |
| `PUT` | `/admin/products/{pid}` | Update product details |
| `PATCH` | `/admin/products/{pid}/status?value=ACTIVE` | Change product status |
| `DELETE` | `/admin/products/{pid}` | Delete product |

**Example — Add Product (`POST /admin/products`):**
```json
{
  "name": "Vijaya Milk",
  "price": 28.00,
  "quantity": "500ml",
  "category": "MILK",
  "imageurl": "https://...",
  "productStatus": "ACTIVE",
  "availableItems": 200
}
```

---

#### Order Management

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/admin/orders/all` | Get all orders (all users, newest first) |
| `GET` | `/admin/orders/status/{status}` | Filter orders by status |
| `GET` | `/admin/orders/{id}` | Get single order by ID |

---

### 🧪 Payment Test API (`/test`)

#### `POST /test/test-signature`
Generate a test Razorpay HMAC signature for development/debugging.

**Request Body:**
```json
{
  "orderId": "order_xxxxx",
  "paymentId": "pay_xxxxx"
}
```

**Response (200):**
```json
{
  "razorpay_order_id": "order_xxxxx",
  "razorpay_payment_id": "pay_xxxxx",
  "razorpay_signature": "generated_hmac_signature",
  "message": "Test signature generated successfully"
}
```

> ⚠️ **Note:** This endpoint should be disabled or secured before final production release.

---

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
| 📱 **Local** | `http://localhost:8080` | `develop` | `vijayadairy_local` |
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
