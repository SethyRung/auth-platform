# Auth Platform

Role-based ticket management system with Keycloak OAuth2/OIDC authentication.

## Architecture

Four services orchestrated via Docker Compose:

| Service         | Technology                                | Port |
| --------------- | ----------------------------------------- | ---- |
| PostgreSQL      | 18-alpine                                 | 5432 |
| Keycloak        | 26.5 (custom build with Vue theme)        | 8080 |
| Spring Boot API | 4.0.3 / Java 25                           | 8000 |
| Vue 3 Web       | Vite + Vue 3 + Nuxt UI + Pinia + Tailwind | 5173 |

PostgreSQL hosts two databases:

- `keycloak` - Keycloak identity provider data
- `auth_platform` - Application data

## Prerequisites

- Docker and Docker Compose
- Java 21+ (for local backend development)
- Node 22+ (for local frontend development)

## Quick Start

1. Copy environment variables:

   ```bash
   cp .env.example .env
   ```

2. Start all services:

   ```bash
   docker-compose up --build
   ```

3. Access services:
   - Web App: http://localhost:5173
   - API: http://localhost:8000
   - API Docs: http://localhost:8000/swagger-ui.html
   - Keycloak Admin: http://localhost:8080/admin (admin/admin)
   - Keycloak Realm: http://localhost:8080/realms/auth-platform

## Keycloak Setup (Manual)

After starting services, configure Keycloak via the admin console:

1. Create a client: `web-client` (public, redirect URI: `http://localhost:5173/callback*`)
2. Create realm roles: `user`, `admin`
3. Create test users and assign roles

## Development

### Backend (service/)

```bash
cd service
./gradlew build          # Build project
./gradlew test           # Run tests
./gradlew bootRun        # Run locally (requires DB and env vars)
```

### Frontend (web/)

```bash
cd web
pnpm install             # Install dependencies
pnpm dev                 # Development server
pnpm build               # Production build (includes type-check)
pnpm keycloak            # Build + inject Keycloak theme
pnpm lint                # Run oxlint
pnpm lint:fix            # Auto-fix lint issues
pnpm fmt                 # Format with oxfmt
pnpm type-check          # TypeScript check only
```

#### Keycloak Theme

The Keycloak login pages use a custom Vue 3 + Nuxt UI theme. To customize:

1. Edit components in `web/src/views/keycloak/` (LoginView, RegisterView, etc.)
2. Run `pnpm keycloak` to build and inject the theme
3. Restart Keycloak container to see changes

Theme data flows from Keycloak → FreeMarker → `window.$kcData` → Vue app.

### Docker Commands

```bash
docker-compose up --build            # Start all services
docker-compose up postgres keycloak  # Start only infra services
docker-compose down -v               # Stop and remove volumes
```

## API Endpoints

### Health

| Method | Endpoint  | Auth   | Description  |
| ------ | --------- | ------ | ------------ |
| GET    | /api/ping | Public | Health check |

### Tickets

| Method | Endpoint                 | Role        | Description                                     |
| ------ | ------------------------ | ----------- | ----------------------------------------------- |
| GET    | /api/tickets             | ADMIN       | List all tickets                                |
| GET    | /api/tickets/my          | USER, ADMIN | List current user's tickets                     |
| GET    | /api/tickets/{id}        | USER, ADMIN | Get a ticket by ID                              |
| POST   | /api/tickets             | USER, ADMIN | Create a ticket                                 |
| PUT    | /api/tickets/{id}        | ADMIN       | Full update of any ticket                       |
| PATCH  | /api/tickets/{id}        | USER        | Partial update of own ticket (no status change) |
| PATCH  | /api/tickets/{id}/status | ADMIN       | Update ticket status only                       |
| DELETE | /api/tickets/{id}        | ADMIN       | Delete a ticket                                 |

### Ticket Model

| Field       | Type           | Values                              |
| ----------- | -------------- | ----------------------------------- |
| id          | Long           | Auto-generated                      |
| title       | String         |                                     |
| description | String         |                                     |
| priority    | TicketPriority | LOW, MEDIUM, HIGH, URGENT           |
| status      | TicketStatus   | OPEN, IN_PROGRESS, RESOLVED, CLOSED |
| createdBy   | String         | Set from JWT subject on creation    |
| createdAt   | LocalDateTime  | Auto-set on creation                |
| updatedAt   | LocalDateTime  | Auto-updated on change              |

## Authentication Flow

1. Frontend redirects user to Keycloak login
2. User authenticates with Keycloak
3. Keycloak returns OAuth2 access token (JWT)
4. Frontend includes JWT in `Authorization: Bearer <token>` header
5. API validates JWT using Keycloak's public keys
6. API extracts user roles from JWT claims

## Security

- JWT tokens validated against Keycloak issuer
- Roles extracted from `realm_access.roles`
- Method-level authorization via `@PreAuthorize`
  Zzz- CORS configured for frontend origins
- Swagger UI available without authentication for API exploration

## Environment Variables

See `.env.example` for all available variables:

```bash
# PostgreSQL
POSTGRES_USER
POSTGRES_PASSWORD

# Keycloak
KC_DB_URL
KC_DATABASE
KC_DB_USERNAME
KC_DB_PASSWORD
KC_BOOTSTRAP_ADMIN_USERNAME
KC_BOOTSTRAP_ADMIN_PASSWORD
KC_HOSTNAME
KC_HOSTNAME_PORT

# Spring Boot API
SERVER_PORT
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
SPRING_DATASOURCE_DB
SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI
SPRING_CORS_ALLOWED_ORIGINS
```

## 🛡️ License

This project is licensed under the [MIT License](LICENSE).
