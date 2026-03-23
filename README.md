# RBAC Service

## 1) Start infrastructure (Docker)

Start Eureka + Postgres:

```bash
docker compose up -d eureka-server postgres
```

Check status:

```bash
docker compose ps
```

Expected:

- `rbac-eureka` is running on `http://localhost:8761`
- `rbac-postgres` is `healthy`

## 2) Run app locally (from your machine)

```bash
mvn spring-boot:run
```

Local defaults used by `application.yml`:

- DB URL: `jdbc:postgresql://localhost:5439/rbac_db`
- DB user: `postgres`
- DB pass: `postgres`
- Eureka URL: `http://localhost:8761/eureka`
- App port: `8088`

## 3) Run app in Docker Compose (optional)

First build image:

```bash
mvn clean package
mvn compile jib:dockerBuild
```

Then run app + infra together:

```bash
docker compose --profile app up -d
```

In this mode:

- app connects DB via hostname `postgres`
- app registers to Eureka at `http://eureka-server:8761/eureka`

## Troubleshooting `JDBCConnectionException`

If you still get:

`org.hibernate.exception.JDBCConnectionException: unable to obtain isolated JDBC connection`

verify these in order:

1. DB container is healthy:

   ```bash
   docker compose ps
   ```

2. Port mapping is available on host:

   ```bash
   docker compose port postgres 5432
   ```

   It should resolve to `0.0.0.0:5439` (or your overridden `DB_PORT`).

3. Credentials match what app uses:

   - `DB_USERNAME`
   - `DB_PASSWORD`
   - `DB_NAME`

4. If app runs **on host**, DB host must be `localhost`.
   If app runs **in Docker**, DB host must be `postgres`.

5. You can override at runtime explicitly:

   ```bash
   DB_HOST=localhost DB_PORT=5439 DB_NAME=rbac_db DB_USERNAME=postgres DB_PASSWORD=postgres EUREKA_DEFAULT_ZONE=http://localhost:8761/eureka mvn spring-boot:run
   ```

## Troubleshooting circular dependency (`Requested bean is currently in creation`)

If startup fails around `ApiKeyFilter -> ApiKeyService -> SecurityConfig -> ApiKeyFilter`,
ensure `PasswordEncoder` is provided from a separate config class (`PasswordConfig`) and not from `SecurityConfig` itself.
This project already applies that fix.
