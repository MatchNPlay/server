# Use a lightweight JDK base image (Java 21)
FROM eclipse-temurin:21-jdk-alpine

# Set working directory in container
WORKDIR /app

# Copy the built jar file
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar

# Expose port (default spring boot port)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
