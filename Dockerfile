# Usa una imagen base con Java 17 (compatible con Spring Boot 3+)
FROM eclipse-temurin:17-jdk-jammy

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copia el JAR de tu API
COPY target/api-musapi-0.0.1-SNAPSHOT.jar app.jar

# Puerto que expone la API (debe coincidir con server.port de Spring Boot)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]