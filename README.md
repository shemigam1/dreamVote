# dreamVote Server

Spring Boot REST API for the dreamVote voting application. Handles voter
registration, login, election creation, candidate nomination, and vote
casting. Persists to MongoDB.

**Live:** https://dreamvote-server.onrender.com
**Frontend:** https://dreamvote.vercel.app

## Tech stack

- **Java 25** (LTS)
- **Spring Boot 4.1.0-M3** (milestone — see note below)
- **Maven** (`./mvnw`)
- **MongoDB 7** via Spring Data MongoDB
- **Spring Boot Actuator** for liveness / readiness probes
- **Lombok**

## Prerequisites

- **JDK 25** (Eclipse Temurin recommended)
- **Docker** + **Docker Compose** for the easiest local setup
- A MongoDB instance — either via the included `docker-compose.yml`,
  a local install, or [MongoDB Atlas](https://cloud.mongodb.com)

## Local development

### Option A — Docker Compose (easiest)

```sh
docker compose up --build
```

Spins up:
- `mongo` on `localhost:27017` (with a named volume for data persistence)
- `app` (this Spring Boot service) on `localhost:8080`

The API service waits for Mongo to pass its healthcheck before starting.

Verify:
```sh
curl http://localhost:8080/actuator/health        # {"status":"UP"}
```

### Option B — IDE / `./mvnw`

If you prefer running the JVM directly (faster iteration):

1. Start a MongoDB somehow (e.g. `docker run -p 27017:27017 mongo:7`).
2. Run with the docker profile and Mongo URI:

   ```sh
   SPRING_PROFILES_ACTIVE=docker \
   SPRING_MONGODB_URI=mongodb://localhost:27017/dreamVote \
     ./mvnw spring-boot:run
   ```

   Or set those as environment variables in your IDE's run configuration.

## Environment variables

| Variable | Default | Purpose |
|---|---|---|
| `SPRING_PROFILES_ACTIVE` | `docker` (baked into Dockerfile) | Activates `application-docker.yml` |
| `SPRING_MONGODB_URI` | `mongodb://mongo:27017/dreamVote` | MongoDB connection string |
| `CORS_ALLOWED_ORIGINS` | `http://localhost:5173,http://127.0.0.1:5173` | Comma-separated allowed origins (no spaces) |

For deployed environments, override `SPRING_MONGODB_URI` with an Atlas
connection string and `CORS_ALLOWED_ORIGINS` with your frontend's URL.

## Health endpoints

Powered by Spring Boot Actuator. Used by Docker / Render / Kubernetes
probes:

| Endpoint | Use |
|---|---|
| `/actuator/health` | Overall app health |
| `/actuator/health/liveness` | "Is the JVM responsive?" — restart on failure |
| `/actuator/health/readiness` | "Are dependencies (Mongo) reachable?" — drop from load balancer on failure |

## Project structure

```
src/main/java/dreamVote/dreamdev/
├── Main.java                 # @SpringBootApplication entry point
├── config/                   # CORS configuration (env-driven)
├── controllers/              # @RestController endpoints (/voters, /elections)
├── dtos/                     # Request / response DTOs
├── exceptions/               # Domain exceptions
├── models/                   # MongoDB @Document entities
├── repositories/             # Spring Data Mongo repositories
├── services/                 # Business logic
└── utils/                    # Shared helpers

src/main/resources/
├── application.properties    # Default config
└── application-docker.yml    # Profile-specific config (Mongo URI from env, Actuator endpoints)
```

## Deployment

The service is deployed to **Render** as a Docker Web Service. Render
auto-detects the `Dockerfile` and builds the image on every push to `main`.

Required environment variables in Render:

```
SPRING_MONGODB_URI=mongodb+srv://<user>:<password>@<cluster>.mongodb.net/dreamVote?...
CORS_ALLOWED_ORIGINS=https://dreamvote.vercel.app
```

Health Check Path: `/actuator/health/readiness`

> Render free tier sleeps after 15 minutes of inactivity. First request
> after idle takes ~30 seconds (cold start).


