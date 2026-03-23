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
- Port: `5432`
- Database: `rbac_db`
- Username: `postgres`
- Password: `postgres`

These defaults match the Spring config in `src/main/resources/application.yml`.

## Run the application

```bash
mvn spring-boot:run
```

Application port: `8088`
