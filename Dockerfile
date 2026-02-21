# Use Eclipse Temurin OpenJDK 17 as base image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy entire project
COPY . .

# Build the entire project
RUN apt-get update && apt-get install -y maven && \
    mvn clean package -DskipTests -pl lanjii-application -am

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "lanjii-application/target/lanjii-application-3.0.0.jar"]