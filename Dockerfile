# syntax=docker/dockerfile:1.7


FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app

COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 mvn -B -e -ntp dependency:go-offline


COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -B -e -ntp clean package -DskipTests


FROM eclipse-temurin:25-jre
WORKDIR /app


RUN apt-get update \
 && apt-get install -y --no-install-recommends curl \
 && rm -rf /var/lib/apt/lists/*


RUN useradd --system --uid 1001 --no-create-home --shell /usr/sbin/nologin spring


ENV SPRING_PROFILES_ACTIVE=docker
ENV SPRING_MONGODB_URI=mongodb+srv://semil:semil@cluster0.tuovuly.mongodb.net/dreamVote?appName=Cluster0


COPY --from=build --chown=spring:spring /app/target/dreamVote.jar app.jar

USER spring

EXPOSE 8080


HEALTHCHECK --interval=10s --timeout=3s --start-period=30s --retries=5 \
  CMD curl -fsS http://localhost:8080/actuator/health/readiness || exit 1

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
