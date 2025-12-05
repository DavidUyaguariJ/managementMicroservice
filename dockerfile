#Etapa 1:
FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -D skipTests
#Etapa 2:
FROM openjdk:25-ea-21-slim
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY --from=builder /app/${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
