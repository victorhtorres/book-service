# Use Amazon Corretto 21 as the base image
FROM amazoncorretto:21

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/book-service-api.jar /app/book-service-api.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/book-service-api.jar"]