# Use Eclipse Temurin OpenJDK 17 as base image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy parent pom.xml
COPY ../pom.xml ./pom-parent.xml

# Copy pom.xml and adjust relativePath
COPY pom.xml .
RUN sed -i 's|<parent>|<parent>\n        <relativePath>pom-parent.xml</relativePath>|g' pom.xml

# Install Maven and download dependencies
RUN apt-get update && apt-get install -y maven && mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/lanjii-application-3.0.0.jar"]