# 🤖 AI Helpdesk Chatbot System

> A powerful, intelligent helpdesk support system powered by Spring Boot and AI, designed to automate ticket management, provide intelligent responses, and streamline customer support workflows.

[![Java Version](https://img.shields.io/badge/Java-21+-orange.svg)](https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.msi (sha256))
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0+-green.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)]()
[![Maven](https://img.shields.io/badge/Maven-3.8+-red.svg)](https://maven.apache.org/)

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [Installation](#installation)
- [Configuration](#configuration)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Usage Examples](#usage-examples)
- [Database Schema](#database-schema)
- [Contributing](#contributing)
- [License](#license)
- [Support](#support)

---

## ✨ Features

### Core Features

- **AI-Powered Ticket Generation** - Automatically create and categorize support tickets using AI
- **Intelligent Ticket Routing** - Smart assignment of tickets to appropriate support agents
- **Real-time Status Tracking** - Monitor ticket status and resolution progress
- **Natural Language Processing** - Understand customer queries in natural language
- **Automated Responses** - Generate context-aware responses using Spring AI
- **Priority Management** - Automatic ticket prioritization based on urgency
- **User Authentication** - Secure JWT-based authentication
- **Role-Based Access Control** - Admin, Agent, and User roles with granular permissions

### 🔒 Security Features

- **BCrypt Password Encryption** - Enterprise-grade password security
- **JWT Authentication** - Token-based stateless authentication
- **Input Validation** - Comprehensive request validation
- **SQL Injection Prevention** - Parameterized queries via JPA
- **CORS Support** - Configurable cross-origin resource sharing

### 📊 Data Management

- **Persistent Storage** - Database-backed ticket and user management
- **Transaction Management** - Atomic operations with rollback support
- **Custom Query Support** - Advanced filtering and search
- **Pagination** - Efficient handling of large datasets
- **Sorting & Filtering** - Flexible data retrieval

---

## 🛠 Tech Stack

### Backend Framework
- **Spring Boot 3.0+** - Modern, production-ready framework
- **Spring Web (MVC)** - REST API development
- **Spring Data JPA** - Object-relational mapping and database access
- **Spring Security & OAuth2** - Authentication and authorization
- **Spring AI** - AI/ML integration and tool management

### Database
- **MySQL / PostgreSQL** - Primary relational database

### Security & Validation
- **Spring Security** - Authentication and authorization
- **JWT (JSON Web Tokens)** - Stateless authentication
- **Jakarta Validation API** - Request validation
- **BCrypt** - Password encryption

### Utilities & Productivity
- **Lombok** - Reduce boilerplate code
- **MapStruct** (Optional) - Entity-DTO mapping
- **SLF4J + Logback** - Logging framework
- **Maven** - Dependency management and build tool

### Development Tools
- **Spring Boot DevTools** - Fast application restarts
- **Swagger/Springdoc OpenAPI** - API documentation
- **Postman** - API Testing

### Testing (Recommended)
- **JUnit 5** - Unit testing framework
- **Mockito** - Mocking library
- **Spring Boot Test** - Integration testing

## 🏗 Architecture

### Layered Architecture Pattern

```
┌─────────────────────────────────────────┐
│          CLIENT (Web/Mobile)            │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│      REST Controllers Layer              │
│   (Handle HTTP Requests/Responses)      │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│      Service Layer                       │
│ (Business Logic, Validation, AI Tool)   │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│    Repository Layer (Data Access)        │
│     (JPA, Custom Queries)                │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│      Database Layer                      │
│    (MySQL/PostgreSQL)                    │
└─────────────────────────────────────────┘
```

### Component Interaction

```
TicketController
       ↓
TicketService → TicketValidator → TicketDatabaseTool (AI)
       ↓
TicketRepository
       ↓
Ticket Entity
       ↓
Database
```

---

## 🚀 Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17 or higher** - [Download]((https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.msi (sha256))
- **Maven 3.8+** - [Download](https://maven.apache.org/download.cgi)
- **MySQL 8.0+ or PostgreSQL 12+** - [Download](https://www.mysql.com/downloads/)
- **Git** - [Download](https://git-scm.com/)
- **IDE** - IntelliJ IDEA, VS Code, or Eclipse

### Quick Start (5 minutes)

```bash
# 1. Clone the repository
git clone https://github.com/yourusername/helpdesk-backend.git
cd helpdesk-backend

# 2. Create application.yml configuration (see Configuration section)
# Copy sample configuration and update database credentials

# 3. Build the project
mvn clean install

# 4. Run the application
mvn spring-boot:run

# 5. Access the API
# Base URL: http://localhost:8080/api/v1
# Health Check: http://localhost:8080/actuator/health
```

---

## 📦 Installation

### Step 1: Clone Repository

```bash
git clone https://github.com/yourusername/helpdesk-backend.git
cd helpdesk-backend
```

### Step 2: Set Up Database

```bash
# For MySQL
mysql -u root -p
CREATE DATABASE helpdesk_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'helpdesk_user'@'localhost' IDENTIFIED BY 'secure_password';
GRANT ALL PRIVILEGES ON helpdesk_db.* TO 'helpdesk_user'@'localhost';
FLUSH PRIVILEGES;
```

### Step 3: Configure Application

Create `src/main/resources/application.yml`:

```yaml
server:
  port: 8080
  servlet:
    context-path: /

spring:
  application:
    name: helpdesk-backend
  
  datasource:
    url: jdbc:mysql://localhost:3306/helpdesk_db?useSSL=false&serverTimezone=UTC
    username: helpdesk_user
    password: secure_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: validate  # Use 'create-drop' for testing, 'validate' for production
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        format_sql: true
    show-sql: false  # Set to true for debugging
  
  security:
    jwt:
      secret: ${JWT_SECRET:your-secret-key-change-in-production}
      expiration: 86400000  # 24 hours in milliseconds
  
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      model: gpt-4

logging:
  level:
    root: INFO
    com.substring.helpdesk: DEBUG
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/application.log
    max-size: 10MB
    max-history: 30

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: when-authorized
```

### Step 4: Build & Run

```bash
# Build
mvn clean install

# Run
mvn spring-boot:run

# Or use JAR directly
java -jar target/helpdesk-backend-1.0.0.jar
```

### Step 5: Verify Installation

```bash
# Check health endpoint
curl http://localhost:8080/actuator/health

# Expected response:
# {"status":"UP"}
```

---

## ⚙️ Configuration

### Environment Variables

```bash
# Database Configuration
DB_URL=jdbc:mysql://localhost:3306/helpdesk_db
DB_USERNAME=helpdesk_user
DB_PASSWORD=your_secure_password

# JWT Configuration
JWT_SECRET=your-super-secret-key-min-32-chars
JWT_EXPIRATION=86400000

# AI Configuration
OPENAI_API_KEY=sk-xxxxx
AI_MODEL=gpt-4

# Email Configuration (Optional)
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password
```

### Application Profiles

```bash
# Development
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Production
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
```

---

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api/v1
```

### Authentication
All endpoints require JWT token in the Authorization header:
```bash
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Key Endpoints

#### 👤 User Management

```bash
# Register User
POST /users
Content-Type: application/json

{
  "username": "john_doe",
  "password": "SecurePass123!",
  "email": "john@example.com",
  "fullName": "John Doe"
}

# Response (201 Created)
{
  "success": true,
  "message": "User created successfully",
  "data": {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "role": "USER",
    "createdAt": "2024-01-15T10:30:00"
  }
}

# Get User
GET /users/{id}

# Get User by Username
GET /users/username/{username}

# Update Password
PUT /users/{id}/password
{
  "oldPassword": "OldPass123!",
  "newPassword": "NewPass456!",
  "confirmPassword": "NewPass456!"
}
```

#### 🎟️ Ticket Management

```bash
# Create Ticket
POST /tickets
Content-Type: application/json

{
  "title": "Login Issue",
  "description": "Cannot login to account",
  "userEmail": "user@example.com",
  "priority": "HIGH",
  "categoryName": "Account"
}

# Response (201 Created)
{
  "success": true,
  "message": "Ticket created successfully",
  "data": {
    "id": 101,
    "title": "Login Issue",
    "status": "OPEN",
    "priority": "HIGH",
    "createdAt": "2024-01-15T10:30:00"
  }
}

# Get Ticket
GET /tickets/{id}

# Get Ticket by Email
GET /tickets/email/{email}

# Update Ticket
PUT /tickets/{id}
{
  "title": "Updated Title",
  "status": "IN_PROGRESS",
  "assignedTo": "agent@company.com"
}

# Delete Ticket
DELETE /tickets/{id}

# Get Tickets by Status
GET /tickets/status/{status}
```

#### 🤖 AI Tools

```bash
# Create Ticket via AI
POST /ai/tickets/create
{
  "description": "I can't reset my password"
}

# Get AI Response
POST /ai/chat
{
  "ticketId": 101,
  "message": "What's the status of my ticket?"
}
```

### Error Responses

```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "username": "Username is required",
    "password": "Password must be at least 8 characters"
  },
  "timestamp": "2024-01-15T10:30:00"
}
```

### Status Codes

| Code | Meaning |
|------|---------|
| 200 | OK - Request successful |
| 201 | Created - Resource created |
| 400 | Bad Request - Invalid input |
| 401 | Unauthorized - Authentication required |
| 403 | Forbidden - Insufficient permissions |
| 404 | Not Found - Resource not found |
| 500 | Internal Server Error |

---

## 📁 Project Structure

```
helpdesk-backend/
├── src/
│   ├── main/
│   │   ├── java/com/substring/helpdesk/
│   │   │   ├── config/              # Configuration classes
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── ApplicationConfig.java
│   │   │   ├── security/            # JWT & Security
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   └── JwtTokenProvider.java
│   │   │   ├── controller/          # REST endpoints
│   │   │   │   ├── TicketController.java
│   │   │   │   └── UserController.java
│   │   │   ├── service/             # Business logic
│   │   │   │   ├── TicketService.java
│   │   │   │   └── UserService.java
│   │   │   ├── repository/          # Data access
│   │   │   │   ├── TicketRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── entity/              # JPA entities
│   │   │   │   ├── Ticket.java
│   │   │   │   └── User.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── request/
│   │   │   │   │   ├── CreateTicketRequest.java
│   │   │   │   │   └── CreateUserRequest.java
│   │   │   │   ├── response/
│   │   │   │   │   ├── TicketResponse.java
│   │   │   │   │   ├── UserResponse.java
│   │   │   │   │   └── ApiResponse.java
│   │   │   │   └── mapper/
│   │   │   │       ├── TicketMapper.java
│   │   │   │       └── UserMapper.java
│   │   │   ├── enums/               # Enumerations
│   │   │   │   ├── Priority.java
│   │   │   │   ├── TicketStatus.java
│   │   │   │   └── UserRole.java
│   │   │   ├── exception/           # Custom exceptions
│   │   │   │   ├── TicketNotFoundException.java
│   │   │   │   ├── InvalidUserException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── tools/               # Spring AI tools
│   │   │   │   ├── TicketDatabaseTool.java
│   │   │   │   └── NotificationTool.java
│   │   │   ├── utils/               # Utility classes
│   │   │   │   ├── TicketValidator.java
│   │   │   │   ├── EmailValidator.java
│   │   │   │   └── Constants.java
│   │   │   └── HelpDeskBackendApplication.java  # Main class
│   │   └── resources/
│   │       ├── application.yml      # Configuration
│   │       └── db/
│   │           └── migration/       # DB migrations (Flyway/Liquibase)
│   │
│   └── test/
│       └── java/com/substring/helpdesk/
│           ├── service/             # Service tests
│           ├── controller/          # Controller tests
│           └── repository/          # Repository tests
│
├── pom.xml                          # Maven configuration
├── README.md                        # This file
├── LICENSE                          # License
├── .gitignore                       # Git ignore rules
├── docker-compose.yml               # Docker setup
└── docs/                            # Documentation
    ├── API.md
    ├── ARCHITECTURE.md
    └── INSTALLATION.md
```

---

## 💡 Usage Examples

### Example 1: Create a Support Ticket

```bash
curl -X POST http://localhost:8080/api/v1/tickets \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Application Crash",
    "description": "App crashes when uploading files",
    "userEmail": "user@example.com",
    "priority": "CRITICAL",
    "categoryName": "Bug Report"
  }'
```

### Example 2: Get All Open Tickets

```bash
curl -X GET http://localhost:8080/api/v1/tickets/status/OPEN \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Example 3: Update Ticket Status

```bash
curl -X PUT http://localhost:8080/api/v1/tickets/101 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "status": "IN_PROGRESS",
    "assignedTo": "agent@company.com"
  }'
```

### Example 4: AI-Powered Response

```bash
curl -X POST http://localhost:8080/api/v1/ai/tickets/create \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "description": "I forgot my password and need to reset it"
  }'
```

---

## 🗄️ Database Schema

### User Table
```sql
CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(100) UNIQUE,
  role ENUM('ADMIN', 'AGENT', 'USER', 'GUEST'),
  is_active BOOLEAN DEFAULT TRUE,
  full_name VARCHAR(100),
  profile_image_url VARCHAR(500),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  last_login_at TIMESTAMP
);
```

### Ticket Table
```sql
CREATE TABLE help_desk_tickets (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(200) NOT NULL,
  description TEXT NOT NULL,
  user_email VARCHAR(100) NOT NULL,
  status ENUM('OPEN', 'IN_PROGRESS', 'RESOLVED', 'CLOSED', 'ON_HOLD'),
  priority ENUM('LOW', 'MEDIUM', 'HIGH', 'CRITICAL'),
  category_name VARCHAR(100),
  assigned_to VARCHAR(100),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_user_email (user_email),
  INDEX idx_status (status),
  INDEX idx_created_at (created_at)
);
```

---

## 🧪 Testing

### Run All Tests
```bash
mvn test
```

### Run Specific Test
```bash
mvn test -Dtest=TicketServiceTest
```

### Run with Coverage
```bash
mvn test jacoco:report
```

### Example Unit Test
```java
@SpringBootTest
class TicketServiceTest {
    
    @MockBean
    private TicketRepository ticketRepository;
    
    @InjectMocks
    private TicketService ticketService;
    
    @Test
    void shouldCreateTicketSuccessfully() {
        // Arrange
        CreateTicketRequest request = CreateTicketRequest.builder()
            .title("Test Ticket")
            .description("Test Description")
            .userEmail("test@example.com")
            .priority(Priority.HIGH)
            .build();
        
        // Act
        TicketResponse response = ticketService.createTicket(request);
        
        // Assert
        assertNotNull(response);
        assertEquals("Test Ticket", response.getTitle());
    }
}
```

---

## 🐳 Docker Deployment

### Build Docker Image
```bash
docker build -t helpdesk-backend:latest .
```

### Run with Docker Compose
```bash
docker-compose up -d
```

### Docker Compose File
```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root_password
      MYSQL_DATABASE: helpdesk_db
      MYSQL_USER: helpdesk_user
      MYSQL_PASSWORD: user_password
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
  
  helpdesk-backend:
    build: .
    environment:
      DB_URL: jdbc:mysql://mysql:3306/helpdesk_db
      DB_USERNAME: helpdesk_user
      DB_PASSWORD: user_password
      JWT_SECRET: your-secret-key
    ports:
      - "8080:8080"
    depends_on:
      - mysql
    volumes:
      - ./logs:/app/logs

volumes:
  mysql_data:
```

---

## 🤝 Contributing

We welcome contributions! Please follow these steps:

### 1. Fork the Repository
```bash
git clone https://github.com/yourusername/helpdesk-backend.git
cd helpdesk-backend
```

### 2. Create Feature Branch
```bash
git checkout -b feature/your-feature-name
```

### 3. Make Changes & Commit
```bash
git add .
git commit -m "feat: add your feature description"
```

### 4. Push to Branch
```bash
git push origin feature/your-feature-name
```

### 5. Create Pull Request
- Go to GitHub and create a pull request
- Describe your changes clearly
- Reference any related issues

### Commit Message Guidelines
```
feat: add new feature
fix: fix a bug
docs: update documentation
style: code style changes
refactor: refactor code
test: add/update tests
chore: update dependencies
```

### Code Style
- Follow Google Java Style Guide
- Use meaningful variable names
- Add JavaDoc comments for public methods
- Max line length: 120 characters

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 📧 Support

### Get Help
- **Issues** - [GitHub Issues](https://github.com/yourusername/helpdesk-backend/issues)
- **Discussions** - [GitHub Discussions](https://github.com/yourusername/helpdesk-backend/discussions)
- **Email** - support@example.com

### Report Bugs
1. Check if issue already exists
2. Provide detailed description
3. Include error logs and stack traces
4. Share environment details (OS, Java version, etc.)

---

## 🎯 Roadmap

### Version 1.0 (Current)
- ✅ User management with JWT authentication
- ✅ Ticket CRUD operations
- ✅ Role-based access control
- ✅ Basic ticket prioritization

### Version 1.1 (Planned)
- 🔄 Advanced AI-powered ticket categorization
- 🔄 Real-time notifications
- 🔄 Email integration
- 🔄 Analytics dashboard

### Version 2.0 (Future)
- 🔄 Multi-channel support (Email, Chat, Social)
- 🔄 Knowledge base integration
- 🔄 Customer satisfaction surveys
- 🔄 Performance optimization

---

## 👥 Authors

- **Shankar** - *Lead Developer* - [@yourGitHub](https://github.com/yourusername)

---

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- OpenAI for AI/ML capabilities
- Community contributors

---

## 📞 Contact

- GitHub: [@yourusername](https://github.com/yourusername)
- Email: your.email@example.com
- LinkedIn: [Your Profile](https://linkedin.com/in/yourprofile)

---

**Made with ❤️ by the Helpdesk Team**

⭐ If you find this project helpful, please give it a star!

---

## 📄 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/projects/spring-data-jpa)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [Spring AI Documentation](https://spring.io/projects/spring-ai)
- [JWT Best Practices](https://tools.ietf.org/html/rfc8949)

---

**Last Updated:** January 2024  
**Version:** 1.0.0
