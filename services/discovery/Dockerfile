# Use Amazon Corretto JDK 21 base image
FROM amazoncorretto:21-alpine as builder

# Set working directory
WORKDIR /app

# Copy the Maven wrapper and pom.xml first
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the source code
COPY . .

# Package the application
RUN ./mvnw clean package -DskipTests

# Final image
FROM amazoncorretto:21-alpine

# Create app directory
WORKDIR /app

# Copy jar from builder
COPY --from=builder /app/target/*.jar app.jar

# Expose application port (override if needed per service)
EXPOSE 8761

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
