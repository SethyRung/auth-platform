# Build stage
FROM gradle:8.14-jdk25-alpine AS build
WORKDIR /app

# Copy only Gradle files first (better caching)
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Download dependencies first (cached layer)
RUN gradle dependencies --no-daemon || true

# Copy source code
COPY src ./src

# Build jar
RUN gradle bootJar --no-daemon -x test

# Runtime stage
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy jar
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]