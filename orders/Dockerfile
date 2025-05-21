# Use a lightweight OpenJDK base image
FROM openjdk:21-jdk-slim

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
COPY wait-for.sh /wait-for.sh
# Copy the built jar into the container
COPY /target/*.jar app.jar
# Expose the port your app listens on (default 8080)
EXPOSE 9090

RUN chmod +x /wait-for.sh
# Run the jar file
ENTRYPOINT ["/wait-for.sh", "http://service-registry:8761", "configuration-server", "java", "-jar", "app.jar"]