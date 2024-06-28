FROM openjdk:11-jdk-slim

WORKDIR /app

COPY target/http-client-0.0.1-SNAPSHOT.jar /app/http-client.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/http-client.jar"]
