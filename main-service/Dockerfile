FROM openjdk:11-jdk-slim

WORKDIR /app

COPY target/main-service-0.0.1-SNAPSHOT.jar /app/main-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/main-service.jar"]
