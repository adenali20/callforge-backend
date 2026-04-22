#FROM openjdk:17-jdk-alpine
FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} callforge-backend.jar
EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/callforge-backend.jar"]