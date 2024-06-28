FROM openjdk:11-jdk-slim

WORKDIR /app

COPY target/http-server-0.0.1-SNAPSHOT.jar /app/http-server.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/http-server.jar"]
