# online-banking-system
A full-stack Online Banking System built with Java Microservices and React.js, offering secure transaction management, user authentication, and modular service architecture.

# Online Banking System – Java Microservices + React.js

This project is a scalable and secure online banking platform built using Java-based Microservices architecture for the backend and React.js for the frontend. The system enables users to manage their bank accounts, perform transactions, and view financial activity in real time.

## 🧱 Architecture Overview
- **Microservices**: Each core function (e.g., User Service, Account Service, Transaction Service) is independently developed and deployed
- **API Gateway**: Centralized routing and load balancing for all services
- **Service Discovery**: Eureka-based registry for inter-service communication
- **Frontend**: React.js with REST API integration for dynamic UI rendering

## 👥 User Roles
- **Customer**: Register/login, view balances, transfer funds, view statements
- **Admin**: Manage users, monitor transactions, and handle escalations

## 🔧 Tech Stack
- **Frontend**: React.js, Axios, Bootstrap
- **Backend**: Java, Spring Boot (Microservices), Spring Cloud, REST APIs
- **Security**: Spring Security with JWT-based authentication
- **Database**: MySQL for persistent storage, Redis (optional) for caching
- **Other Tools**: Eureka, Spring Cloud Gateway, Config Server, Docker (optional)

## ✨ Key Features
- User registration and secure login
- Account management and transaction history
- Fund transfer between users
- Role-based access and session management
- RESTful APIs with JSON-based communication
- Scalable Microservices architecture with service discovery
- Responsive frontend with React

## 🚀 Getting Started

### Backend Setup
1. Clone the backend microservices (`user-service`, `account-service`, `transaction-service`, `api-gateway`, `config-server`, etc.)
2. Configure MySQL credentials in each service's `application.yml`
3. Start Config Server, Eureka, and API Gateway in order
4. Run all services using `Spring Boot` or Docker Compose

### Frontend Setup
1. Clone the `react-frontend` repo
2. Navigate to the project directory and run:
   ```bash
   npm install
   npm start
