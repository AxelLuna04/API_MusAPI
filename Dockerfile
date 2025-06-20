# Dockerfile en raíz del proyecto
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
RUN mkdir -p /app/uploads
ENTRYPOINT ["java", "-jar", "app.jar"]
