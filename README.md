# 🧾 mPOS Order Service

A backend system simulating a **mobile Point-of-Sale (mPOS)** application using **Spring Boot**, providing REST APIs for product and order management, along with QR code generation for order confirmation.

![](swagger%20ui.PNG)

---

## 📺 Walkthrough Video

### Watch the walkthrough video here: [Walkthrough Video](https://drive.google.com/file/d/1cPv7VGHNuum3im0RNbQBjexnMoIgm7fy/view?usp=sharing)
---

## 🚀 Tech Stack

- Java 21
- Spring Boot
- Maven
- H2 In-Memory Database
- ZXing (QR Code Generator)
- Swagger UI

---

## 📦 Features

### 1. Product Management

| Method | Endpoint     | Description           |
|--------|--------------|-----------------------|
| GET    | `/products`  | Get all products      |
| POST   | `/products`  | Add a new product     |

**Sample Product Object:**

```json
{
  "name": "Test Product 1",
  "category": "Electronics",
  "price": 50.00,
  "stock": 100
},
{
  "name": "Test Product 2",
  "category": "Electronics",
  "price": 500.00,
  "stock": 100
}
```

---

### 2. Order Management

| Method | Endpoint    | Description        |
|--------|-------------|--------------------|
| GET    | `/orders`   | List all orders    |
| POST   | `/orders`   | Create a new order |

**Order Request Example:**

```json
{
  "customerName": "Test Customer",
  "ordersItems": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 3
    },
  ]
}
```

**Order Response Example:**

```json
{
  "id": 1,
  "customerName": "Test Customer",
  "orderId": "ORD123456",
  "totalAmount": 1600,
  "items": [
    {
      "id": 1,
      "productName": "Test Product 1",
      "quantity": 2
    },
    {
      "id": 2,
      "productName": "Test Product 2",
      "quantity": 3
    }
  ],
  "bas64QRCode": "iVBORw0KGgoAAAANSUhEUg..."
}
```

---

### 3. 📲 QR Code Generation

- On successful order creation, a **QR Code** is generated and returned as a **Base64 image**.
- **QR Format Example:**
  ```
  OrderID: ORD123456
  Total: Rs. 1600
  Customer: Test Customer
  ```

---

## 🛠️ How to Run

1. **Clone the repo:**

```bash
git clone https://github.com/gitesh8/Apeirosai-Assignment
cd Apeirosai-Assignment
```

2. **Run the app:**

```bash
Use any IDE that supports springboot
```

3. **Access APIs and Swagger UI:**

- APIs: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui/index.html`

---

## 📋 API Summary

### Product Endpoints
- `GET /products` → Returns a list of all products
- `POST /products` → Creates a new product

### Order Endpoints
- `GET /orders` → Returns all orders with details
- `POST /orders` → Creates an order and returns order details + Base64 QR

---

## 📄 License

This project is developed for educational and demonstration purposes.

---

