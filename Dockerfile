# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS build

# Set working directory
WORKDIR /app

# Service directory is passed as build arg (e.g., services/config-server)
ARG SERVICE_DIR

# Copy the service's build files
COPY ${SERVICE_DIR}/pom.xml ./
COPY ${SERVICE_DIR}/.mvn/ .mvn/
COPY ${SERVICE_DIR}/mvnw ./

# Make mvnw executable
RUN chmod +x mvnw

# Download dependencies and plugins (this layer will be cached unless POM changes)
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw dependency:go-offline -B

# Copy the service source code
COPY ${SERVICE_DIR}/src/ ./src/

# Build the application (this layer will be rebuilt only if source code changes)
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

# Set environment variables
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=50.0"
ENV SPRING_PROFILES_ACTIVE=prod

# Create app directory and set working directory
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Run as non-root user for better security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Expose default port
EXPOSE 8080

# Set the entry point
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]