# asg-backend-682110178

## 📖 Project Overview

**Student Course Management System** — A Spring Boot REST API for managing students, courses, enrollments, and profiles. Built for DII-Backend-2026 assignment.

### Key Features
- **4 JPA Entities** with bidirectional relationships (Student, Profile, Course, Enrollment)
- **3 Relationship Types**: One-to-One, Many-to-One, One-to-Many
- **Full CRUD + PATCH** REST endpoints for all entities
- **Bean Validation** on all DTOs
- **H2 In-Memory Database** with web console
- **35 Unit Tests** (Mockito) — all passing
- **Multi-module Maven** (domain-model, web-service, web-front)

---

## 🚀 Getting Started

### Prerequisites
- **Java 11+** (verified: OpenJDK 11+)
- **Maven 3.8+**

### Quick Start
```bash
# 1. Clone the repository
git clone https://github.com/camtdii/asg-backend-682110178.git
cd asg-backend-682110178

# 2. Build all modules
mvn clean install

# 3. Run the REST API (web-service module)
cd web-service
mvn spring-boot:run
```

### Verify It Works
| Check | URL |
|-------|-----|
| **API Base** | http://localhost:8080 |
| **H2 Console** | http://localhost:8080/h2-console |
| **JDBC URL** | `jdbc:h2:mem:assignment_db` |
| **Username** | `sa` |
| **Password** | *(leave empty)* |

### Run Tests
```bash
# From project root
mvn test
```
Expected: **Tests run: 35, Failures: 0, Errors: 0, Skipped: 0**

---

## 📐 Domain Model

### Entities & Relationships

```
┌─────────────┐       ┌─────────────┐
│  Student    │◄──────│  Profile    │
│             │ 1:1   │             │
│ id          │──────►│ id          │
│ name        │       │ bio         │
│ email       │       │ phone       │
└──────┬──────┘       │ address     │
       │              └─────────────┘
       │ 1:N
       ▼
┌─────────────┐       ┌─────────────┐
│ Enrollment  │──────►│   Course    │
│             │ N:1   │             │
│ id          │       │ id          │
│ enrolledAt  │       │ name        │
│ status      │       │ description │
└──────┬──────┘       │ credits     │
       │              └──────┬──────┘
       │ N:1                │ 1:N
       ▼                    │
┌─────────────┐             │
│  Student    │◄────────────┘
└─────────────┘
```

### Relationship Types

| Type | Example | Entities |
|------|---------|----------|
| **One-to-One** | Student ↔ Profile | `Student.profile` / `Profile.student` |
| **Many-to-One** | Enrollment → Student | `Enrollment.student` |
| **Many-to-One** | Enrollment → Course | `Enrollment.course` |
| **One-to-Many** | Course → Enrollments | `Course.enrollments` |
| **One-to-Many** | Student → Enrollments | `Student.enrollments` |

> **Total: 4 entities, 3 relationship types** ✅

---

## 🛠 Tech Stack

| Layer | Technology |
|-------|------------|
| **Framework** | Spring Boot 2.3.0.RELEASE |
| **Language** | Java 11 |
| **ORM** | Spring Data JPA (Hibernate) |
| **Database** | H2 In-Memory (dev), MySQL (prod config available) |
| **Build** | Maven 3.8+ (multi-module) |
| **Testing** | JUnit 5 + Mockito |
| **Validation** | Bean Validation (Hibernate Validator) |

---

## 📦 Project Structure

```
| asg-backend-682110178/
├── domain-model/          # JPA Entities + Repositories (jar)
│   └── th.mfu.domain      # Student, Profile, Course, Enrollment
│   └── th.mfu.repository  # Spring Data JPA Repositories
├── web-service/           # Spring Boot REST API (jar, runs on :8080)
│   └── th.mfu.dto         # Request/Response DTOs
│   └── th.mfu.service     # Business Logic Services
│   └── th.mfu.controller  # REST Controllers
│   └── th.mfu.App         # Main Application
└── web-front/             # Web Frontend (war, Jetty on :8081)
```

---

## 🚀 Running the Application

### Prerequisites
- Java 11+
- Maven 3.8+

### Build & Run
```bash
# From project root
mvn clean install

# Run web-service
cd web-service
mvn spring-boot:run
```

**Application starts on:** `http://localhost:8080`

**H2 Console:** `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:assignment_db`
- Username: `sa`
- Password: *(empty)*

---

## 📚 REST API Endpoints

### Students (`/api/students`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/` | Create student (with optional profile) |
| GET | `/` | List all students |
| GET | `/{id}` | Get student by ID |
| GET | `/email/{email}` | Get student by email |
| PATCH | `/{id}` | Update student |
| DELETE | `/{id}` | Delete student |

### Courses (`/api/courses`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/` | Create course |
| GET | `/` | List all courses |
| GET | `/{id}` | Get course by ID |
| PATCH | `/{id}` | Update course |
| DELETE | `/{id}` | Delete course |

### Enrollments (`/api/enrollments`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/` | Create enrollment (requires studentId, courseId) |
| GET | `/` | List all enrollments |
| GET | `/{id}` | Get enrollment by ID |
| GET | `/student/{studentId}` | Get enrollments by student |
| GET | `/course/{courseId}` | Get enrollments by course |
| PATCH | `/{id}` | Update enrollment status |
| DELETE | `/{id}` | Delete enrollment |

### Profiles (`/api/profiles`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/student/{studentId}` | Create profile for student |
| GET | `/{id}` | Get profile by ID |
| GET | `/student/{studentId}` | Get profile by student ID |
| PATCH | `/{id}` | Update profile |
| DELETE | `/{id}` | Delete profile |

---

## 🤖 AI Usage Declaration

This project was developed with assistance from **AI coding agents**:

| Tool | Purpose |
|------|---------|
| **Hermes Agent** | Architecture design, code scaffolding, test generation, H2 configuration, Maven setup |
| **Composio MCP** | GitHub operations (fork, push), Google Classroom/Drive integration |

**Human contributions:**
- Domain model design decisions (entity selection, relationship types)
- Requirement interpretation from assignment PPTX
- Code review and verification
- Test strategy approval
- Final submission decisions

**AI-generated artifacts:**
- All JPA entities with bidirectional relationship management
- Spring Data JPA repositories
- DTOs with Bean Validation annotations
- Service layer with transactional boundaries
- REST controllers with full CRUD + PATCH
- 35 unit tests (Mockito-based controller tests)
- H2 in-memory database configuration
- Maven dependency fixes (validation starter, Jakarta → javax migration)
- README.md documentation

All AI-generated code was reviewed, compiled, and verified with `mvn clean test` (35/35 tests passing).