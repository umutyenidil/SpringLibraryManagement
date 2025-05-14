# Stage 1: Build the application using a shared Maven cache volume
FROM amazoncorretto:21-alpine AS build

WORKDIR /app

# Copy only files needed for dependency resolution
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies using shared Maven repo
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw clean package -DskipTests

# Stage 2: Create a lightweight runtime image
FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

RUN apk add --no-cache curl

# Expose application port (override if needed per service)
EXPOSE 8083

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
