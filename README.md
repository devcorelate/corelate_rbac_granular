# RBAC Service

## Run PostgreSQL with Docker Compose

Start PostgreSQL:

```bash
docker compose up -d postgres
```

Stop PostgreSQL:

```bash
docker compose down
```

The database is exposed at:

- Host: `localhost`
- Port: `5439`
- Database: `rbac_db`
- Username: `postgres`
- Password: `postgres`

These defaults match the Spring config in `src/main/resources/application.yml`.

## Run the application

```bash
mvn spring-boot:run
```

Application port: `8088`

## Troubleshooting JDBCConnectionException

If you get:

`org.hibernate.exception.JDBCConnectionException: unable to obtain isolated JDBC connection`

check that Postgres is running and your app is pointing to the same host/port:

```bash
docker compose ps
```

Expected DB URL default is:

`jdbc:postgresql://localhost:5439/rbac_db`
