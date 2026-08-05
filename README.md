# DII Backend Assignment — Student Course Management System

> **Assignment:** Backend Development (DII-Backend-2026)
> **Due:** 9 August 2026, 16:59
> **Repository:** https://github.com/Pinont/asg-backend-682110178

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

### Relationship Types (3 required)

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
asg-backend-682110178/
├── domain-model/          # JPA Entities + Repositories (jar)
│   └── th.camt.domain     # Student, Profile, Course, Enrollment
│   └── th.camt.repository # Spring Data JPA Repositories
├── web-service/           # Spring Boot REST API (jar, runs on :8080)
│   └── th.camt.dto        # Request/Response DTOs
│   └── th.camt.service    # Business Logic Services
│   └── th.camt.controller # REST Controllers
│   └── th.camt.App        # Main Application
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

## ✅ Test Results

```
Tests run: 35, Failures: 0, Errors: 0, Skipped: 0
```

| Test Class | Tests | Status |
|------------|-------|--------|
| StudentControllerTest | 8 | ✅ Pass |
| CourseControllerTest | 8 | ✅ Pass |
| ProfileControllerTest | 9 | ✅ Pass |
| EnrollmentControllerTest | 10 | ✅ Pass |

Run tests:
```bash
mvn test
```

---

## 🤖 AI Usage Declaration

This project was developed with assistance from **AI coding agents**:

| Tool | Purpose |
|------|---------|
| **Hermes Agent (Ciel)** | Architecture design, code scaffolding, test generation, H2 configuration, Maven setup |
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

---

## 📝 Submission Checklist

- [x] H2 database configured (`application.properties`)
- [x] ≥ 4 entities with 3 relationship types (1:1, N:1, 1:N)
- [x] Entities, Repositories, Services, Controllers, DTOs for each entity
- [x] CRUD endpoints (Create, List, Update/Patch, Delete) per entity
- [x] ≥ 4 unit tests for controllers (35 total, all passing)
- [x] README.md with domain model diagram + AI declaration
- [x] Code pushed to GitHub Classroom repo
- [ ] Submit by **9 August 2026, 16:59** via Google Classroom
- [ ] Ready for in-person code walkthrough

---

## 🔗 Links

- **GitHub Repository:** https://github.com/Pinont/asg-backend-682110178
- **Original Boilerplate:** https://github.com/camtdii/asg-backend
- **Google Classroom Assignment:** https://classroom.google.com/c/ODcxMTk4MTc5MDI3/a/ODcxMzkwOTYyOTcw/details