# Use a lightweight OpenJDK base image
FROM openjdk:21-jdk-slim
# RUN apt-get update && \
#     apt-get install -y curl python3 python3-requests && \
#     rm -rf /var/lib/apt/lists/*
# # RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
# COPY wait-for.py /wait-for.py
# Copy the built jar into the container
COPY /target/*.jar app.jar
# Expose the port your app listens on (default 8080)
EXPOSE 9090

# RUN chmod +x /wait-for.sh
# RUN pip install requests
# Run the jar file
# ENTRYPOINT ["/wait-for.sh", \
#             "http://service-registry:8761", \
#             "configuration-server", \
#             "http://configuration-server:5555/actuator/health", \
#             "java", "-jar", "app.jar"]
ENTRYPOINT ["java", "-jar", "app.jar"]