# Build stage
FROM maven:3.8.7 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy Maven configuration and source files
COPY pom.xml ./
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:21

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file from the build stage
COPY --from=build ./target/cookbobok-0.0.1-SNAPSHOT.jar cookbobok.jar

# Expose the port your app runs on
EXPOSE 8084

# Command to run the JAR file
CMD ["java", "-jar", "cookbook.jar"]
