# Use Eclipse Temurin OpenJDK 17 as base image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy entire project
COPY . .

# Build the entire project
RUN apt-get update && apt-get install -y maven && \
    mvn clean package -DskipTests -pl lanjii-application -am

# Copy entrypoint script
COPY entrypoint.sh /app/
RUN chmod +x /app/entrypoint.sh

# Expose port
EXPOSE 8080

# Run the application using entrypoint script
ENTRYPOINT ["/app/entrypoint.sh"]