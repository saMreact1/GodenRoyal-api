# Use a lightweight JDK runtime
FROM eclipse-temurin:17-jdk-jammy

# Set app directory
WORKDIR /app

# Copy the built JAR into the container
COPY target/GoldenRoyalEmail.jar /app/GoldenRoyalEmail.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "GoldenRoyalEmail.jar"]
