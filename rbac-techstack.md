# RBAC Service — Tech Stack Specification

## Overview
A standalone, multi-tenant **Role-Based Access Control (RBAC) backend service** built using **Spring Boot** and **Java**, exposing REST APIs for authentication, user management, role management, permission management, and permission validation.

This service is **backend only** and intended to be consumed by other applications through secured APIs.

---

## Target Stack

### Core Backend
- **Framework:** Spring Boot
- **Language:** Java
- **Build Tool:** Maven
- **Service Discovery:** Eureka Client
- **Container Build:** Jib Maven Plugin
- **Configuration:** `application.yml`
- **Server Port:** `8088`

### Persistence
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA
- **JPA Provider:** Hibernate

### Security
- **Authentication:** JWT-based authentication
- **Tenant Identification:** API Key via request header
- **Password Hashing:** BCrypt
- **Refresh Token Support:** Yes

### API Style
- **Architecture:** REST API
- **Response Format:** JSON
- **Scope:** Backend APIs only, no frontend/admin panel included

---

## Project Purpose
This RBAC service centralizes authorization logic so that external systems can:
- register/login users
- manage users, roles, and permissions
- assign roles to users
- assign permissions to roles
- validate if a user has a specific permission

Each client application acts as a **tenant**, and all RBAC data is isolated per tenant.

---

## Multi-Tenant Design
Each tenant is represented by a **client application**.

All major records are scoped by:
- `client_app_id`

Tenant isolation applies to:
- users
- roles
- permissions
- refresh tokens
- audit logs

Tenant identification is done through:
- `X-API-Key` request header

---

## Recommended Spring Boot Modules / Dependencies

### Required Dependencies
- Spring Web
- Spring Data JPA
- PostgreSQL Driver
- Spring Security
- Validation
- Eureka Discovery Client
- Lombok
- JWT library
- Spring Boot Actuator

### Maven-related Notes
Use Maven for:
- compiling the project
- dependency management
- packaging JAR
- container build through **Jib**

---

## Suggested Maven Add-ons
- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-security`
- `spring-boot-starter-validation`
- `spring-boot-starter-actuator`
- `spring-cloud-starter-netflix-eureka-client`
- `postgresql`
- `jjwt` or equivalent JWT library
- `lombok`
- `jib-maven-plugin`

---

## Runtime Configuration
All environment-specific values should be placed in:

- `src/main/resources/application.yml`

### Main config areas
- app name
- server port
- datasource
- JPA/Hibernate
- Eureka client
- JWT settings
- API key settings
- logging
- actuator endpoints

### Required Port
- `server.port: 8088`

---

## Example Configuration Areas in `application.yml`
You said you want env configs there, so the file should include sections like:

- `server`
- `spring.datasource`
- `spring.jpa`
- `eureka.client`
- `management.endpoints`
- `jwt`
- `rbac`

Example structure:
```yml
server:
  port: 8088

spring:
  application:
    name: rbac-service
  datasource:
    url: jdbc:postgresql://localhost:5432/rbac_db
    username: postgres
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka

jwt:
  secret: your-secret-key
  access-token-expiration: 900000
  refresh-token-expiration: 2592000000
```

---

## Database Design
The PostgreSQL schema should support the RBAC structure from the markdown, translated to Spring Boot entities.

### Main Tables
- `client_apps`
- `users`
- `roles`
- `permissions`
- `user_roles`
- `role_permissions`
- `refresh_tokens`
- `audit_logs`

### Entity Relationships
- One `client_app` has many `users`
- One `client_app` has many `roles`
- One `client_app` has many `permissions`
- Many `users` to many `roles`
- Many `roles` to many `permissions`
- One `user` has many `refresh_tokens`

---

## Suggested JPA Entity Model

### Entities
- `ClientApp`
- `User`
- `Role`
- `Permission`
- `UserRole`
- `RolePermission`
- `RefreshToken`
- `AuditLog`

### Common Entity Features
Use JPA/Hibernate annotations for:
- `@Entity`
- `@Table`
- `@Id`
- `@GeneratedValue`
- `@Column`
- `@ManyToOne`
- `@OneToMany`
- `@ManyToMany`
- `@JoinColumn`
- `@JoinTable`

Recommended shared fields:
- `id`
- `createdAt`
- `updatedAt`
- `deletedAt` or `isDeleted` if soft delete is needed

---

## Security Model

### Layer 1 — API Key
Each request includes:
- `X-API-Key`

Purpose:
- identify tenant
- reject invalid or inactive clients

### Layer 2 — JWT
Protected endpoints additionally require:
- `Authorization: Bearer <token>`

Purpose:
- authenticate user
- authorize protected operations

### Token Design
- **access_token** = short-lived
- **refresh_token** = long-lived
- rotate refresh token on refresh request

---

## Backend API Scope
Only backend APIs are included.

### Core API Groups

#### 1. Health
- `GET /api/health`

#### 2. Auth
- `POST /api/auth/login`
- `POST /api/auth/refresh`
- `POST /api/auth/logout`

#### 3. Permission Check
- `POST /api/check`

#### 4. Users
- `GET /api/users`
- `POST /api/users`
- `GET /api/users/{id}`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`
- `POST /api/users/{id}/roles`
- `DELETE /api/users/{id}/roles/{roleId}`

#### 5. Roles
- `GET /api/roles`
- `POST /api/roles`
- `GET /api/roles/{id}`
- `PUT /api/roles/{id}`
- `DELETE /api/roles/{id}`
- `POST /api/roles/{id}/permissions`
- `DELETE /api/roles/{id}/permissions/{permissionId}`

#### 6. Permissions
- `GET /api/permissions`
- `POST /api/permissions`
- `GET /api/permissions/{id}`
- `PUT /api/permissions/{id}`
- `DELETE /api/permissions/{id}`

---

## Spring Boot Package Structure
Recommended clean package structure:

```text
com.yourcompany.rbac
├── config
├── controller
├── dto
├── entity
├── repository
├── security
├── service
├── mapper
├── exception
├── filter
├── util
└── client
```

### Suggested Contents
- `config` → security, swagger, bean configs
- `controller` → REST controllers
- `dto` → request/response DTOs
- `entity` → JPA entities
- `repository` → JPA repositories
- `security` → JWT provider, user details, auth config
- `service` → business logic
- `filter` → API key filter, JWT filter
- `exception` → global exception handling

---

## Key Backend Components

### Controllers
- `AuthController`
- `CheckController`
- `UserController`
- `RoleController`
- `PermissionController`
- `HealthController`

### Services
- `AuthService`
- `JwtService`
- `ApiKeyService`
- `UserService`
- `RoleService`
- `PermissionService`
- `RbacService`
- `RefreshTokenService`
- `AuditLogService`

### Repositories
- `ClientAppRepository`
- `UserRepository`
- `RoleRepository`
- `PermissionRepository`
- `RefreshTokenRepository`
- `AuditLogRepository`

---

## Recommended Security Filters
Implement request filters/interceptors for:

- **ApiKeyFilter**
  - validates `X-API-Key`
  - resolves tenant/client app

- **JwtAuthenticationFilter**
  - validates bearer token
  - loads authenticated user context

---

## DTO Suggestions

### Request DTOs
- `LoginRequest`
- `RefreshTokenRequest`
- `CreateUserRequest`
- `UpdateUserRequest`
- `AssignRoleRequest`
- `CreateRoleRequest`
- `UpdateRoleRequest`
- `AssignPermissionRequest`
- `CreatePermissionRequest`
- `UpdatePermissionRequest`
- `PermissionCheckRequest`

### Response DTOs
- `ApiResponse<T>`
- `LoginResponse`
- `TokenResponse`
- `UserResponse`
- `RoleResponse`
- `PermissionResponse`
- `PermissionCheckResponse`

---

## Response Standard
Keep a unified JSON response structure like:

```json
{
  "status": "success",
  "message": "Request successful",
  "data": {}
}
```

For validation or business errors:

```json
{
  "status": "error",
  "message": "Validation failed",
  "errors": {
    "email": "must be a valid email"
  }
}
```

---

## Build and Containerization

### Maven Build
Standard build flow:
```bash
mvn clean install
```

### Docker Image Build via Jib
Since you want Jib:
```bash
mvn compile jib:dockerBuild
```

This allows container image generation without writing a Dockerfile.

### Recommended Image Output
Example image name:
- `rbac-service:latest`

---

## Eureka Registration
This service should register itself to Eureka so other services can discover it.

### Suggested Service Name
- `rbac-service`

### Needed Behavior
- register with Eureka on startup
- expose health endpoint
- optionally expose actuator info

---

## Suggested API Base Path
Recommended base path:
- `/api`

Examples:
- `/api/auth/login`
- `/api/users`
- `/api/roles`
- `/api/check`

---

## Suggested Non-Functional Notes
- use soft delete where needed
- enforce tenant isolation in every query
- validate that role/permission/user belongs to authenticated tenant
- hash API keys before saving
- hash passwords with BCrypt
- never expose refresh token hash directly
- add audit logging for key actions
- support token rotation
- add global exception handler

---

## Minimal Technical Summary
If you want a shorter version for Codex prompt, use this:

```md
Build a backend-only multi-tenant RBAC service using Spring Boot and Java.

Tech stack:
- Spring Boot
- Java
- PostgreSQL
- Spring Data JPA
- Hibernate
- Spring Security
- Maven
- Eureka Client
- Jib for Docker image build
- application.yml for environment configuration

Requirements:
- Run on port 8088
- Backend APIs only
- REST API for auth, users, roles, permissions, and permission checking
- Multi-tenant via X-API-Key header
- JWT authentication with refresh token support
- BCrypt password hashing
- PostgreSQL persistence with JPA/Hibernate
- Maven project
- Jib-compatible for Docker image build
- Eureka service registration
- Standard JSON API response format
- Use application.yml for datasource, JWT, Eureka, and server configs
```
