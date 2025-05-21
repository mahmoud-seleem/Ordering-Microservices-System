FROM openjdk:21-jdk-slim

# Copy the built jar into the container
COPY /target/*.jar app.jar

# Expose the port your app listens on (default 8080)
EXPOSE 5555

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app.jar"]